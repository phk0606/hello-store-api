package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.OrderProduct;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class OrderProductDto {

    private Long orderProductId;
    private int salePrice;
    private int orderQuantity;
    private Integer point;
    private Integer orderShippingFee;
    private int totalPrice;
    @Setter
    private List<OrderProductOptionDto> orderProductOptions = new ArrayList<>();

    public OrderProductDto(OrderProduct orderProduct) {
        this.orderProductId = orderProduct.getId();
        this.salePrice = orderProduct.getSalePrice();
        this.orderQuantity = orderProduct.getOrderQuantity();
        this.point = orderProduct.getPoint();
        this.orderShippingFee = orderProduct.getOrderShippingFee();
        this.totalPrice = orderProduct.getTotalPrice();
    }

    @QueryProjection
    public OrderProductDto(Long orderProductId, int salePrice, int orderQuantity, Integer point, Integer orderShippingFee, int totalPrice) {
        this.orderProductId = orderProductId;
        this.salePrice = salePrice;
        this.orderQuantity = orderQuantity;
        this.point = point;
        this.orderShippingFee = orderShippingFee;
        this.totalPrice = totalPrice;
    }
}
