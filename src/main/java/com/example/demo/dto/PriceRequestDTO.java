package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriceRequestDTO {

    private Integer brandId;
    private Integer productId;
    private LocalDateTime requestDate;

}
