package com.example.demo.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PriceRequestDTO {

    @NotNull
    private Integer brandId;

    @NotNull
    private Integer productId;

    @NotNull
    private LocalDateTime requestDate;

}
