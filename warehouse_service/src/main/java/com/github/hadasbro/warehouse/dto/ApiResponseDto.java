package com.github.hadasbro.warehouse.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@SuppressWarnings({"unused"})
@Data @NoArgsConstructor
@ToString
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

}