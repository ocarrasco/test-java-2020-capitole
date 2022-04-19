package com.example.demo.services.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.dto.PriceRequestDTO;
import com.example.demo.dto.PriceResponseDTO;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PriceServiceImplTest {

    @Autowired
    private PriceServiceImpl priceService;

    @Test
    void notFound() {
        var request = new PriceRequestDTO(2, 222, LocalDateTime.now());
        var response = priceService.retrieve(request);
        assertThat(response).isEmpty();
    }

    @Test
    void retrievingPriceListOne() {
        var request = new PriceRequestDTO(1, 35455, LocalDateTime.parse("2020-06-14T10:00:00"));
        var response = priceService.retrieve(request);
        assertThat(response).isPresent();
        assertThat(response.get().getBrandId()).isEqualTo(1);
        assertThat(response.get().getProductId()).isEqualTo(35455);
        assertThat(response.get().getPriceList()).isEqualTo(1);
        assertThat(response.get().getFinalPrice()).isEqualTo(35.50);
        checkDates(response, "2020-06-14T00:00:00", "2020-12-31T23:59:59");
    }

    @Test
    void retrievingPriceListTwo() {
        var request = new PriceRequestDTO(1, 35455, LocalDateTime.parse("2020-06-14T16:00:00"));
        var response = priceService.retrieve(request);
        assertThat(response).isPresent();
        assertThat(response.get().getBrandId()).isEqualTo(1);
        assertThat(response.get().getProductId()).isEqualTo(35455);
        assertThat(response.get().getPriceList()).isEqualTo(2);
        assertThat(response.get().getFinalPrice()).isEqualTo(25.45);
        checkDates(response, "2020-06-14T15:00:00", "2020-06-14T18:30:00");
    }

    @Test
    void retrievingPriceListThree() {
        var request = new PriceRequestDTO(1, 35455, LocalDateTime.parse("2020-06-14T21:00:00"));
        var response = priceService.retrieve(request);
        assertThat(response).isPresent();
        assertThat(response.get().getPriceList()).isEqualTo(1);
        checkDates(response, "2020-06-14T00:00:00", "2020-12-31T23:59:59");
    }

    @Test
    void retrievingPriceListFour() {
        var request = new PriceRequestDTO(1, 35455, LocalDateTime.parse("2020-06-15T10:00:00"));
        var response = priceService.retrieve(request);
        assertThat(response).isPresent();
        assertThat(response.get().getPriceList()).isEqualTo(3);
        assertThat(response.get().getFinalPrice()).isEqualTo(30.50);
        checkDates(response, "2020-06-15T00:00:00", "2020-06-15T11:00:00");
    }

    @Test
    void retrievingPriceListFive() {
        var request = new PriceRequestDTO(1, 35455, LocalDateTime.parse("2020-06-16T21:00:00"));
        var response = priceService.retrieve(request);
        assertThat(response).isPresent();
        assertThat(response.get().getPriceList()).isEqualTo(4);
        assertThat(response.get().getFinalPrice()).isEqualTo(38.95);
        checkDates(response, "2020-06-15T16:00:00", "2020-12-31T23:59:59");
    }

    private void checkDates(Optional<PriceResponseDTO> responseDTO, String startDate, String endDate) {
        assertThat(responseDTO.get().getStartDate()).isEqualTo(startDate);
        assertThat(responseDTO.get().getEndDate()).isEqualTo(endDate);
    }

}