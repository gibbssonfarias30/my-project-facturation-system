package com.backfcdev.advancedspringrestapis.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    private Integer id;
    private String name;
    private String description;
}
