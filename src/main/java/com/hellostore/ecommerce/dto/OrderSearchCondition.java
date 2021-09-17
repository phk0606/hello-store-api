package com.hellostore.ecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderSearchCondition {

    private String username;
    private String orderDateA;
    private String orderDateB;
}