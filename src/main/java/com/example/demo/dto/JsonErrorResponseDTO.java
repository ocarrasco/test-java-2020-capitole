package com.example.demo.dto;

import org.springframework.validation.FieldError;

import lombok.Data;

@Data
public class JsonErrorResponseDTO {

    private String field;
    private String message;

    public static JsonErrorResponseDTO from(FieldError fieldError) {
        var dto = new JsonErrorResponseDTO();
        dto.field = fieldError.getField();
        dto.message = fieldError.getCode();
        return dto;
    }

}
