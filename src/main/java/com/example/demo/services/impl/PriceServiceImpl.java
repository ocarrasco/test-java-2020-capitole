package com.example.demo.services.impl;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.example.demo.dto.PriceRequestDTO;
import com.example.demo.dto.PriceResponseDTO;
import com.example.demo.repositories.PricesRepository;
import com.example.demo.services.IPriceService;
import com.example.demo.utils.DateUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements IPriceService {

    private final PricesRepository pricesRepository;

    @Override
    public Optional<PriceResponseDTO> retrieve(PriceRequestDTO priceRequestDTO) {
        var brandId = priceRequestDTO.getBrandId();
        var productId = priceRequestDTO.getProductId();
        var requestDatetime = DateUtil.toUTCDate(priceRequestDTO.getRequestDate());
        var pageRequest = PageRequest.of(0, 1);
        var response = pricesRepository.findFirstOrderByPriorityDesc(brandId, productId, requestDatetime, pageRequest);
        return Optional.ofNullable(response.isEmpty() ? null : PriceResponseDTO.from(response.get(0)));
    }

}
