package com.backfcdev.advancedspringrestapis.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDTO {
    private Integer id;
    private String name;
    private double price;
    private String imageUrl;
    private CategoryDTO category;
}
