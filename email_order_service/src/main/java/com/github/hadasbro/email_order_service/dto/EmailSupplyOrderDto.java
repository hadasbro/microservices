package com.github.hadasbro.email_order_service.dto;

import com.github.hadasbro.email_order_service.domain.EmailOrder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
@Data
final public class EmailSupplyOrderDto {

    private String senderEmail;
    private List<ProductQty> productQtys;

    /**
     * ProductQty
     */
    @SuppressWarnings("unused")
    public static class ProductQty implements Serializable {

        private int quantity;
        private String catalogId;

        /**
         * ProductQty
         * @param quantity -
         * @param catalogId -
         */
        public ProductQty(int quantity, String catalogId) {
            this.quantity = quantity;
            this.catalogId = catalogId;
        }

        /**
         * getQuantity
         * @return int
         */
        public int getQuantity() {
            return quantity;
        }

        /**
         * getCatalogId
         *
         * @return String
         */
        public String getCatalogId() {
            return catalogId;
        }

        /**
         * from
         *
         * @param prod -
         * @return ProductQty
         */
        public static ProductQty from(com.github.hadasbro.email_order_service.domain.ProductQty prod) {
            return new ProductQty(prod.getQuantity(), prod.getCatalogId());
        }
    }

    /**
     * from
     *
     * @param emailOrder -
     * @return EmailSupplyOrderDto -
     */
    public static EmailSupplyOrderDto from(EmailOrder emailOrder) {

        EmailSupplyOrderDto eod = new EmailSupplyOrderDto();
        eod.setSenderEmail(emailOrder.getSenderEmail());
        eod.setProductQtys(
                emailOrder
                .getProducts()
                        .stream()
                        .map(ProductQty::from)
                        .collect(Collectors.toList())
        );

        return eod;
    }
}