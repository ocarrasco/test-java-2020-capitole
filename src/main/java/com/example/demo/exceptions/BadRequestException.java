package com.example.demo.exceptions;

import org.springframework.validation.BindingResult;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private final BindingResult bindingResult;

    public BadRequestException(BindingResult bindingResult) {
        super("BadRequest");
        this.bindingResult = bindingResult;
    }

}
