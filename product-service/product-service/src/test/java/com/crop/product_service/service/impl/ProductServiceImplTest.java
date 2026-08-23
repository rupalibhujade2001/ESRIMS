package com.crop.product_service.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crop.product_service.dto.ProductResponse;
import com.crop.product_service.entity.Product;
import com.crop.product_service.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;


    @Test
    void shouldGetProductSuccessfully() {

        // Arrange
        Product product = Product.builder()
                .id(1L)
                .email("farmer@gmail.com")
                .price(100L)
                .name("Apple")
                .category("Fruit")
                .description("Fresh Apple")
                .imageUrl("apple.jpg")
                .active(true)
                .offerPercentage(10.0)
                .build();

        
        
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        ProductResponse response =
                productService.findProductById(1L);


        // Assert
        assertNotNull(response);

        assertEquals(1L, response.id());
        assertEquals("Apple", response.name());
        assertEquals("Fruit", response.category());
        assertEquals(100L, response.price());
        assertEquals("farmer@gmail.com", response.email());


        // Verify
        verify(productRepository, times(1))
                .findById(1L);
    }
    
    
    
}