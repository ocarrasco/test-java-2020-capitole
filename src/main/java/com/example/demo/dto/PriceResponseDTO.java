package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.model.Prices;
import com.example.demo.utils.DateUtil;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceResponseDTO {

    private Integer brandId;
    private Integer productId;
    private Integer priceList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double finalPrice;

    public static PriceResponseDTO from(Prices prices) {
        return PriceResponseDTO.builder()
                .brandId(prices.getBrand().getId())
                .productId(prices.getProduct().getId())
                .priceList(prices.getPriceList())
                .startDate(DateUtil.toLocalDateTime(prices.getStartDate()))
                .endDate(DateUtil.toLocalDateTime(prices.getEndDate()))
                .finalPrice(prices.getPrice())
                .build();
    }

}
