package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.OrderProductOption;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class OrderProductDto {

    private int salePrice;
    private int orderQuantity;
    private int point;
    private int orderShippingFee;
    private int totalPrice;
    private List<OrderProductOptionDto> orderProductOptions = new ArrayList<>();
}
