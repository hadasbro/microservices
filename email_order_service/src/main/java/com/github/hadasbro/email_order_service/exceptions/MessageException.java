package com.github.hadasbro.email_order_service.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings({"unused"})
public class MessageException extends Exception {

    private CODES code = CODES.GENERAL;

    @Override
    public String toString() {
        return "MessageException{" +
                "code=" + code +
                "exception=" + super.toString() +
                '}';
    }

    public enum CODES {
        GENERAL("general error"),
        INCORRECT_EMAIL("Wrong email"),
        INCORRECT_ORDER("Wrong order format"),
        EMPTY_ORDER("Order is Empty"),
        EMAIL_BLOCKED("Email blocked"),
        EMAIL_NOT_REGISTERED("Email not registered");

        public String msg;
        CODES(String msg){
            this.msg = msg;
        }
    }

    /**
     * MessageException
     */
    public MessageException() {}

    /**
     * MessageException
     *
     * @param code -
     */
    public MessageException(CODES code) {
        super(code.msg);
        this.code = code;
    }

    /**
     * MessageException
     *
     * @param code -
     * @param err -
     */
    public MessageException(CODES code, Throwable err) {
        super(code.msg, err);
        this.code = code;
    }

    /**
     * MessageException
     *
     * @param errorMessage -
     * @param err -
     */
    public MessageException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}
