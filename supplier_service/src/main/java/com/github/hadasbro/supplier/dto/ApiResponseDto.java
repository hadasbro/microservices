package com.github.hadasbro.supplier.dto;

import com.github.hadasbro.supplier.exception.OrderValidationException;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings({"unused", "WeakerAccess"})
@Data @NoArgsConstructor
final public class ApiResponseDto {

    private CODE code = CODE.OK;
    private String message = "ok";

    public enum CODE {
        OK, INVALID_INPUT, GENERAL
    }

    /**
     * ApiResponseDto
     *
     * @param message -
     * @param code    -
     */
    public ApiResponseDto(String message, CODE code) {
        this.code = code;
        this.message = message;
    }

    /**
     * ok
     *
     * @return ApiResponseDto -
     */
    public static ApiResponseDto ok() {
        return new ApiResponseDto();
    }

    /**
     * from
     *
     * @param exc -
     * @return ApiResponseDto -
     */
    public static ApiResponseDto from(OrderValidationException exc) {

        CODE code;

        switch (exc.getCode()) {

            case INVALID_INPUT:
                code = CODE.INVALID_INPUT;
                break;

            case GENERAL:
            default:
                code = CODE.GENERAL;
                break;

        }

        return new ApiResponseDto(exc.getMessage(), code);

    }

}