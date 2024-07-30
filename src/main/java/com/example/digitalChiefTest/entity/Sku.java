package com.example.digitalChiefTest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "skus")
@Getter
@Setter
@NoArgsConstructor
public class Sku {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private String color;
    private Boolean availability;
    private String size;
}

