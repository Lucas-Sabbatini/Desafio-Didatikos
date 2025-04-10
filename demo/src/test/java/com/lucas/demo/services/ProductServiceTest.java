package com.lucas.demo.services;

import com.lucas.demo.dto.ProductRequestDTO;
import com.lucas.demo.models.City;
import com.lucas.demo.models.Product;
import com.lucas.demo.repository.CityRepository;
import com.lucas.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        City city = new City(1L, "City1");
        Product product1 = new Product(1L, "Product 1", BigDecimal.valueOf(10.0), 5, city);
        Product product2 = new Product(2L, "Product 2", BigDecimal.valueOf(20.0), 10, city);

        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        List<Product> products = productService.findAll();

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdFound() {
        Long productId = 1L;
        City city = new City(1L, "City1");
        Product product = new Product(productId, "Product 1", BigDecimal.valueOf(10.0), 5, city);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.findById(productId);

        assertTrue(result.isPresent());
        assertEquals(productId, result.get().getId());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testFindByIdNotFound() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Optional<Product> result = productService.findById(productId);

        assertFalse(result.isPresent());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testCreateProductSuccess() {
        Long cityId = 1L;
        City city = new City(cityId, "City1");
        when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));

        // Instanciando diretamente o DTO (assumindo que é um record)
        ProductRequestDTO dto = new ProductRequestDTO("New Product", BigDecimal.valueOf(15.0), 8, cityId);

        // Produto antes de persistir (com id nulo)
        Product unsavedProduct = new Product(null, "New Product", BigDecimal.valueOf(15.0), 8, city);
        // Produto persistido com id
        Product savedProduct = new Product(1L, "New Product", BigDecimal.valueOf(15.0), 8, city);

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        Product result = productService.createProduct(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Product", result.getName());
        verify(cityRepository, times(1)).findById(cityId);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProductSuccess() {
        Long productId = 1L;
        Long cityId = 1L;
        City city = new City(cityId, "City1");
        Product existingProduct = new Product(productId, "Old Product", BigDecimal.valueOf(10.0), 5, city);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));

        // Instanciando o DTO diretamente
        ProductRequestDTO dto = new ProductRequestDTO("Updated Product", BigDecimal.valueOf(25.0), 12, cityId);

        Product updatedProduct = new Product(productId, "Updated Product", BigDecimal.valueOf(25.0), 12, city);
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.updateProduct(productId, dto);

        assertNotNull(result);
        assertEquals("Updated Product", result.getName());
        assertEquals(BigDecimal.valueOf(25.0), result.getPrice());
        verify(cityRepository, times(1)).findById(cityId);
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProductNotFound() {
        Long productId = 1L;
        Long cityId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        when(cityRepository.findById(cityId)).thenReturn(Optional.of(new City(cityId, "City1")));

        ProductRequestDTO dto = new ProductRequestDTO("Updated Product", BigDecimal.valueOf(25.0), 12, cityId);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.updateProduct(productId, dto);
        });

        assertEquals("Produto não encontrado com ID: " + productId, exception.getMessage());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testDeleteProduct() {
        Long productId = 1L;
        productService.deleteProduct(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }
}
