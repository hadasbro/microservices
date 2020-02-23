package com.github.hadasbro.stock_service.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * EmailSupplyOrderDto
 */
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
         * @return String
         */
        public String getCatalogId() {
            return catalogId;
        }

    }

}