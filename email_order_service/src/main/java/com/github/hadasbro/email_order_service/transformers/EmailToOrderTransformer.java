package com.github.hadasbro.email_order_service.transformers;

import com.github.hadasbro.email_order_service.domain.ProductQty;
import com.github.hadasbro.email_order_service.exceptions.MessageException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.integration.mail.transformer.AbstractMailMessageTransformer;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;
import com.github.hadasbro.email_order_service.domain.EmailOrder;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailToOrderTransformer extends AbstractMailMessageTransformer<EmailOrder> {
    /**
     * orderTags
     */
    private static Pair<String, String> orderTags = Pair.of("[order_start]", "[order_end]");

    /**
     * orderPattern
     */
    private static Pattern orderPattern = Pattern.compile(
            "([0-9]+)(\\s?x\\s?)([0-9]+)",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
    );

    /**
     * doTransform
     *
     * @param mailMessage -
     * @return AbstractIntegrationMessageBuilder<EmailOrder> -
     */
    @Override
    protected AbstractIntegrationMessageBuilder<EmailOrder> doTransform(Message mailMessage) {

        EmailOrder emailOrder;

        try {
            emailOrder = processEmailOrder(mailMessage);
        } catch (MessageException e) {
            emailOrder = (new EmailOrder());
            emailOrder.setMessageException(e);
        }

        return MessageBuilder.withPayload(emailOrder);

    }

    /**
     * processEmailOrder
     *
     * @param mailMessage -
     * @return EmailOrder -
     * @throws MessageException -
     */
    private EmailOrder processEmailOrder(Message mailMessage) throws MessageException {

        try {
            return EmailOrder.from(mailMessage, getProducts(mailMessage));
        } catch (MessagingException e) {
            throw new MessageException(MessageException.CODES.GENERAL, e);
        }
    }

    /**
     * getProducts
     *
     * @param mailMessage -
     * @return List<ProductQty> -
     * @throws MessageException -
     */
    private List<ProductQty> getProducts(Message mailMessage) throws MessageException {

        List<ProductQty> products = new ArrayList<>();

        String content = getMessageContent(mailMessage);

        if (!(content.contains(orderTags.getLeft()) && content.contains(orderTags.getRight()))) {
            throw new MessageException(MessageException.CODES.INCORRECT_ORDER);
        }

        String orderStr = StringUtils.substringBetween(content, orderTags.getLeft(), orderTags.getRight());
        Matcher m = orderPattern.matcher(orderStr);

        while (m.find()) {
            String itemCatalogId = m.group(1);
            int quantity = Integer.parseInt(m.group(3));
            ProductQty product = new ProductQty();
            product.setCatalogId(itemCatalogId);
            product.setQuantity(quantity);
            products.add(product);
        }

        if (products.isEmpty()) {
            throw new MessageException(MessageException.CODES.EMPTY_ORDER);
        }

        return products;

    }

    /**
     * getMessageContent
     *
     * @param message -
     * @return String -
     * @throws MessageException -
     */
    private String getMessageContent(Message message) throws MessageException {

        try {

            Object content = message.getContent();
            if (content instanceof Multipart) {
                StringBuilder messageContent = new StringBuilder();
                Multipart multipart = (Multipart) content;
                for (int i = 0; i < multipart.getCount(); i++) {
                    Part part = multipart.getBodyPart(i);
                    if (part.isMimeType("text/plain")) {
                        messageContent.append(part.getContent().toString());
                    }
                }
                return messageContent.toString();
            }

            return content.toString();

        } catch (IOException | MessagingException e) {
            throw new MessageException(MessageException.CODES.GENERAL, e);
        }

    }

}
  