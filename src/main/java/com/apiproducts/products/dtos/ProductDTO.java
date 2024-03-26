package com.apiproducts.products.dtos;

public record ProductDTO(
        String id,
        String name,
        Integer price_in_cents
) {
}
