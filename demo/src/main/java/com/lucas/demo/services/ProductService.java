package com.lucas.demo.services;

import com.lucas.demo.dto.ProductRequestDTO;
import com.lucas.demo.models.City;
import com.lucas.demo.models.Product;
import com.lucas.demo.repository.CityRepository;
import com.lucas.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CityRepository cityRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(ProductRequestDTO dto) {
        City city = cityRepository.findById(dto.cityId())
                .orElseThrow(() -> new RuntimeException("Cidade não encontrada com ID: " + dto.cityId()));

        Product product = new Product(null, dto.name(), dto.price(), dto.stock(), city);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductRequestDTO dto) {
        City city = cityRepository.findById(dto.cityId())
                .orElseThrow(() -> new RuntimeException("Cidade não encontrada com ID: " + dto.cityId()));

        return productRepository.findById(id)
                .map(existing -> new Product(id, dto.name(), dto.price(), dto.stock(), city))
                .map(productRepository::save)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
