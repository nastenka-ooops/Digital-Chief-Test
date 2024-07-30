package com.example.digitalChiefTest.util;

import com.example.digitalChiefTest.dto.ProductDto;
import com.example.digitalChiefTest.entity.Product;

public class ProductMapper {
    public static ProductDto mapToProductDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getActive(),
                product.getStartDate(),
                product.getSkuses().stream().map(SkuMapper::mapToSkuDto).toList());
    }
}
