package com.github.hadasbro.email_order_service.domain;

import com.github.hadasbro.email_order_service.exceptions.MessageException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@SuppressWarnings("WeakerAccess")
@Data
@NoArgsConstructor
@ToString(exclude = {"messageException"})
@Entity
@Table(name = "email_order")
public class EmailOrder {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Transient
  private MessageException messageException;

  @OneToMany(
          cascade = CascadeType.ALL,
          orphanRemoval = true
  )
  private List<ProductQty> products;

  private String senderEmail;
  private int msgNumber;
  private Date msgDate;
  private String msgSubject;

  @Column(columnDefinition="TEXT", length=10485760)
  private String clientResponse;

  private int status = STATUS_UNKNOWN;

  public static final int STATUS_ACCEPTED = 1;
  public static final int STATUS_DECLINED = 2;
  public static final int STATUS_UNKNOWN = 3;

  /**
   * from
   *
   * @param message -
   * @return EmailOrder -
   * @throws MessagingException -
   */
  public static EmailOrder from(Message message) throws MessagingException {

    Address[] froms = message.getFrom();
    String senderEmail = froms == null ? null : ((InternetAddress) froms[0]).getAddress();
    EmailOrder emailOrder = new EmailOrder();
    emailOrder.setMsgNumber(message.getMessageNumber());
    emailOrder.setMsgDate(message.getReceivedDate());
    emailOrder.setMsgSubject(message.getSubject());
    emailOrder.setSenderEmail(senderEmail);

    return emailOrder;

  }

  /**
   * from
   *
   * @param message -
   * @param products -
   * @return EmailOrder -
   * @throws MessagingException -
   */
  public static EmailOrder from(Message message, List<ProductQty> products) throws MessagingException {
        EmailOrder emailOrder = EmailOrder.from(message);
        emailOrder.setProducts(products);
        return emailOrder;
  }

}
