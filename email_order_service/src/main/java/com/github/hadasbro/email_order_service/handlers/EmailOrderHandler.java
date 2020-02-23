package com.github.hadasbro.email_order_service.handlers;

import com.github.hadasbro.email_order_service.domain.EmailOrder;
import com.github.hadasbro.email_order_service.dto.EmailSupplyOrderDto;
import com.github.hadasbro.email_order_service.dto.SupplyOrderBatchDto;
import com.github.hadasbro.email_order_service.services.EmailOrderService;
import com.github.hadasbro.email_order_service.services.graylog.ApploggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * EmailOrderHandler
 */
@SuppressWarnings({"unused", "FieldCanBeLocal"})
@Component
public class EmailOrderHandler implements GenericHandler<EmailOrder> {

    @Autowired
    private ApploggerService apploggerService;

    @Value("${api.url_base}")
    private String apiUriBase = "";

    @Value("${api.url_resource}")
    private String apiUriResource = "";

    @Autowired
    private EmailOrderService emailOrderService;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public EmailOrderHandler() {
    }

    /**
     * sendSupplyOrderRequest
     *
     * @param emailOrderDto -
     * @return Mono<SupplyOrderBatchDto> -
     */
    private Mono<SupplyOrderBatchDto> sendSupplyOrderRequest(EmailSupplyOrderDto emailOrderDto) {

        WebClient client = webClientBuilder
                .baseUrl(this.apiUriBase)
                .defaultCookie("email_order", "1")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return client
                .method(HttpMethod.POST)
                .uri(this.apiUriResource)
                .body(BodyInserters.fromObject(emailOrderDto))
                .retrieve()
                .bodyToMono(SupplyOrderBatchDto.class);
    }

    /**
     * sendConfirmation
     * <p>
     * TODO
     *
     * @param supplyOrder -
     */
    private void sendConfirmation(SupplyOrderBatchDto supplyOrder) {
    }

    /**
     * sendErrorConfirmation
     * <p>
     * TODO
     *
     * @param t -
     */
    private void sendErrorConfirmation(Throwable t) {
    }

    /**
     * logSupplyOrderAttepmt
     * <p>
     * TODO
     *
     * @param supplyOrder -
     */
    private void logSupplyOrderAttepmt(SupplyOrderBatchDto supplyOrder) {
    }

    /**
     * handle
     *
     * TODO: set output channel
     *
     * @param emailOrder
     * @param messageHeaders
     * @return
     */
    @Override
    public Object handle(EmailOrder emailOrder, MessageHeaders messageHeaders) {
        sendSupplyOrderRequest(EmailSupplyOrderDto.from(emailOrder))
                .doOnNext(this::logSupplyOrderAttepmt)
                .subscribe(supplyOrder -> {

                            try {
                                emailOrder.setClientResponse(supplyOrder.toString());
                                emailOrderService.save(emailOrder);
                            } catch (Throwable t) {
                                apploggerService.log(t);
                            }

                            sendConfirmation(supplyOrder);

                        },
                        error -> {
                            apploggerService.log(error);
                            sendErrorConfirmation(error);
                        }
                );

        return null;
    }
}
