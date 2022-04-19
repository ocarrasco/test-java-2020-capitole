package com.example.demo.validations;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.example.demo.dto.PriceRequestDTO;
import com.example.demo.repositories.BrandRepository;
import com.example.demo.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PriceRequestDTOValidator implements Validator {

    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return PriceRequestDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var dto = (PriceRequestDTO) target;
        if (!brandRepository.existsById(dto.getBrandId())) {
            errors.rejectValue("brandId", "validation.not.found");
        }

        if (!productRepository.existsById(dto.getProductId())) {
            errors.rejectValue("productId", "validation.not.found");
        }
    }
}
