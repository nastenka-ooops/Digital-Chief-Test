package com.example.digitalChiefTest.util;

import com.example.digitalChiefTest.dto.SkuDto;
import com.example.digitalChiefTest.entity.Sku;

public class SkuMapper {
    public static SkuDto mapToSkuDto(Sku sku) {
        return new SkuDto(
                sku.getId(),
                sku.getColor(),
                sku.getAvailability(),
                sku.getSize());
    }
}
