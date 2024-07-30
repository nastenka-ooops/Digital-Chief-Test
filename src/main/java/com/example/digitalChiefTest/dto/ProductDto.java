package com.example.digitalChiefTest.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public record ProductDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Boolean active,
        Date startDate,
        List<SkuDto> skus
) {
}
