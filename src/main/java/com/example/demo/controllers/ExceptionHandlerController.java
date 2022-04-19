package com.example.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.demo.dto.JsonErrorResponseDTO;
import com.example.demo.dto.JsonMessageResponseDTO;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotFoundException;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public List<JsonErrorResponseDTO> handleBadRequest(BadRequestException badRequestException) {
        return badRequestException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(JsonErrorResponseDTO::from)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public JsonMessageResponseDTO handleNotFound(NotFoundException notFoundException) {
        var message = notFoundException.getMessage();
        return new JsonMessageResponseDTO(message);
    }

}
