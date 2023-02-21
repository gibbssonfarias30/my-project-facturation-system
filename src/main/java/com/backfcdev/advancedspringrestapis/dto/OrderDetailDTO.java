package com.backfcdev.advancedspringrestapis.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailDTO {
    private Integer id;
    private ProductDTO product;
    private double price;
    private int amount;
    @JsonBackReference
    private OrderDTO order;

    public Double getSubTotal(){
        return price * amount;
    }
}
