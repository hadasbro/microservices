package com.github.hadasbro.stock_service.exception;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("unused")
@Getter @Setter
public class OrderValidationException extends Exception {

    public enum CODE {
        INVALID_INPUT,
        GENERAL,
        PRODUCT_UNAVAILABLE,
        STORE_UNAVAILABLE,
        ORDER_IS_EMPTY
    }

    private CODE code = CODE.INVALID_INPUT;

    /**
     * OrderValidationException
     *
     * @param message -
     */
    public OrderValidationException(String message) {
        super(message);
    }

    /**
     * OrderValidationException
     *
     * @param message -
     * @param code -
     */
    public OrderValidationException(String message, CODE code) {
        super(message);
        this.code = code;
    }

    /**
     * OrderValidationException
     *
     * @param code -
     */
    public OrderValidationException(CODE code) {
        super("");
        this.code = code;
    }
}