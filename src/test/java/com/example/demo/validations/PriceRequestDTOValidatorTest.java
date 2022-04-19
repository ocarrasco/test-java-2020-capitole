package com.example.demo.validations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import com.example.demo.dto.PriceRequestDTO;
import com.example.demo.dto.PriceResponseDTO;
import com.example.demo.repositories.BrandRepository;
import com.example.demo.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
class PriceRequestDTOValidatorTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private PriceRequestDTOValidator validator;

    @BeforeEach
    public void setup() {
        lenient().when(brandRepository.existsById(anyInt())).thenReturn(Boolean.FALSE);
        lenient().when(productRepository.existsById(anyInt())).thenReturn(Boolean.FALSE);
    }

    @Test
    void support() {
        assertThat(validator.supports(PriceRequestDTO.class)).isTrue();
        assertThat(validator.supports(PriceResponseDTO.class)).isFalse();
        assertThat(validator.supports(Object.class)).isFalse();
    }

    @Test
    void elementsNotFound() {
        var dto = PriceRequestDTO.builder().brandId(1).productId(1).build();
        var errors = new BeanPropertyBindingResult(dto, "dto");

        validator.validate(dto, errors);

        assertThat(errors.getFieldError("brandId").getCode()).isEqualTo("validation.not.found");
        assertThat(errors.getFieldError("productId").getCode()).isEqualTo("validation.not.found");
    }

    @Test
    void brandNotFound() {
        when(productRepository.existsById(anyInt())).thenReturn(Boolean.TRUE);
        var dto = PriceRequestDTO.builder().brandId(1).productId(1).build();
        var errors = new BeanPropertyBindingResult(dto, "dto");

        validator.validate(dto, errors);

        assertThat(errors.getFieldErrorCount()).isEqualTo(1);
        assertThat(errors.getFieldError("brandId").getCode()).isEqualTo("validation.not.found");
    }

    @Test
    void productNotFound() {
        when(brandRepository.existsById(anyInt())).thenReturn(Boolean.TRUE);
        var dto = PriceRequestDTO.builder().brandId(1).productId(1).build();
        var errors = new BeanPropertyBindingResult(dto, "dto");

        validator.validate(dto, errors);

        assertThat(errors.getFieldErrorCount()).isEqualTo(1);
        assertThat(errors.getFieldError("productId").getCode()).isEqualTo("validation.not.found");
    }

}