package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.model.Prices;
import com.example.demo.utils.DateUtil;

import lombok.Data;

@Data
public class PriceResponseDTO {

    private Integer brandId;
    private Integer productId;
    private Integer priceList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double finalPrice;

    public static PriceResponseDTO from(Prices prices) {
        var dto = new PriceResponseDTO();
        dto.setBrandId(prices.getBrand().getId());
        dto.setProductId(prices.getProduct().getId());
        dto.setPriceList(prices.getPriceList());
        dto.setStartDate(DateUtil.toLocalDateTime(prices.getStartDate()));
        dto.setEndDate(DateUtil.toLocalDateTime(prices.getEndDate()));
        dto.setFinalPrice(prices.getPrice());
        return dto;
    }

}
