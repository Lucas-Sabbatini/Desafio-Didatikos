package com.lucas.demo.dto;

import java.math.BigDecimal;

public record ProductRequestDTO(String name, BigDecimal price, Integer stock, Long cityId) {
}
