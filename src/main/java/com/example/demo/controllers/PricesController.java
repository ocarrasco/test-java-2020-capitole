package com.example.demo.controllers;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PriceRequestDTO;
import com.example.demo.dto.PriceResponseDTO;
import com.example.demo.services.IPriceService;
import com.example.demo.validations.PriceRequestDTOValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/prices")
public class PricesController extends BaseController {

    public static final String PRICE_NOT_FOUND_MSG = "Price not found";

    private final IPriceService priceService;
    private final PriceRequestDTOValidator priceRequestDTOValidator;

    @PostMapping
    public PriceResponseDTO findPrice(@Valid @RequestBody PriceRequestDTO priceRequestDTO, BindingResult bindingResult) {
        checkRequest(priceRequestDTO, priceRequestDTOValidator, bindingResult);
        log.info("Retrieving prices for {}", priceRequestDTO);
        var response = priceService.retrieve(priceRequestDTO);
        return getOrNotFound(response, PRICE_NOT_FOUND_MSG);
    }

}
