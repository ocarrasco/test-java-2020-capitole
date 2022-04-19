package com.example.demo.controllers;

import java.util.Optional;

import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseController {

    protected void checkRequest(BindingResult bindingResult) {
        log.debug("Performing validation");
        if (bindingResult.hasErrors()) {
            log.debug("{} errors found in request", bindingResult.getFieldErrorCount());
            throw new BadRequestException(bindingResult);
        }
    }

    protected void checkRequest(Object target, Validator validator, BindingResult bindingResult) {
        checkRequest(bindingResult);
        validator.validate(target, bindingResult);
        checkRequest(bindingResult);
    }

    protected <T extends Object> T getOrNotFound(Optional<T> entity, String message) {
        return entity.orElseThrow(() -> new NotFoundException(message));
    }

}
