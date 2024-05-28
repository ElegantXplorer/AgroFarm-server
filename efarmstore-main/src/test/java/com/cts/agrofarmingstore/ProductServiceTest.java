package com.cts.agrofarmingstore;


import com.cts.agrofarmingstore.exception.ResourceNotFoundException;
import com.cts.agrofarmingstore.model.Product;
import com.cts.agrofarmingstore.repository.ProductRepository;
import com.cts.agrofarmingstore.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        List<Product> productList = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(productList);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.getAllProducts();
        });
    }

    @Test
    public void testGetProductById() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(productId);

        Assertions.assertEquals(product, foundProduct);
    }


    @Test
    public void testUpdateProduct_ExistingProduct() {
        Long productId = 1L;
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Existing Product");
        existingProduct.setDescription("Existing Description");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        existingProduct.setName("Updated Name");
        existingProduct.setDescription("Updated Description");

        Product updatedProduct = productService.updateProduct(existingProduct);

        Assertions.assertEquals(existingProduct.getId(), updatedProduct.getId());
        Assertions.assertEquals(existingProduct.getName(), updatedProduct.getName());
        Assertions.assertEquals(existingProduct.getDescription(), updatedProduct.getDescription());
    }

    @Test
    public void testDeleteProduct_ExistingProduct() {
        Long productId = 1L;
        Product existingProduct = new Product();
        existingProduct.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }


}