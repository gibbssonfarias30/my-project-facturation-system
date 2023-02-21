package com.backfcdev.advancedspringrestapis.dto;

import com.backfcdev.advancedspringrestapis.model.Order;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientDTO {
    private Integer id;
    private String name;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    @JsonManagedReference
    private List<OrderDTO> orders;
}
