package com.backfcdev.advancedspringrestapis.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDTO {
    private Integer id;
    @JsonBackReference
    private ClientDTO client;
    @JsonManagedReference
    private List<OrderDetailDTO> orderDetails;

    public Double getTotal(){
        return  orderDetails.stream()
                .mapToDouble(OrderDetailDTO::getSubTotal)
                .sum();
    }
}
