package com.example.demo.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import com.example.demo.dto.PriceRequestDTO;
import com.example.demo.dto.PriceResponseDTO;
import com.example.demo.services.IPriceService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PricesController.class)
class PricesControllerTest {

    private static final String CONTROLLER_URL = "/prices";

    @MockBean
    private IPriceService priceService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void notFound() throws Exception {
        var priceRequestDTO = PriceRequestDTO.builder()
                .brandId(1)
                .productId(1)
                .requestDate(LocalDateTime.now())
                .build();

        when(priceService.retrieve(any())).thenReturn(Optional.empty());

        var request = post(CONTROLLER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(priceRequestDTO));

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(PricesController.PRICE_NOT_FOUND_MSG));
    }

    @Test
    void emptyRequest_badRequest() throws Exception {
        var request = post(CONTROLLER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}");

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(checkFieldInArray("productId", "NotNull"))
                .andExpect(checkFieldInArray("brandId", "NotNull"))
                .andExpect(checkFieldInArray("requestDate", "NotNull"));
    }

    @Test
    void ok() throws Exception {
        var priceRequestDTO = PriceRequestDTO.builder()
                .brandId(1)
                .productId(1)
                .requestDate(LocalDateTime.now())
                .build();

        var priceResponseDTO = PriceResponseDTO.builder()
                .brandId(1)
                .productId(1)
                .build();

        when(priceService.retrieve(any())).thenReturn(Optional.of(priceResponseDTO));

        var request = post(CONTROLLER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(priceRequestDTO));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.productId").value(1));
    }

    private ResultMatcher checkFieldInArray(String fieldName, String messageValue) {
        return jsonPath(String.format("$.[?(@.field == '%s')].message", fieldName)).value(messageValue);
    }

}