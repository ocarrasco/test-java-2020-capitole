package com.example.demo.services;

import java.util.Optional;

import com.example.demo.dto.PriceRequestDTO;
import com.example.demo.dto.PriceResponseDTO;

public interface IPriceService {

    Optional<PriceResponseDTO> retrieve(PriceRequestDTO priceRequestDTO);

}
