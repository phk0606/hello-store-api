package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.OrderProduct;
import com.hellostore.ecommerce.entity.ProductOption;
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
    private Long orderId;
    private Long productId;
    private String productName;
    private int salePrice;
    private int quantity;
    private Integer point;
    private Integer shippingFee;
    private int totalPrice;
    @Setter
    private List<OrderProductOptionDto> productOptions = new ArrayList<>();

    @Setter
    private List<ProductOptionDto> firstOptions = new ArrayList<>();
    @Setter
    private List<ProductOptionDto> secondOptions = new ArrayList<>();

    @Setter
    private byte[] image;
    private String fileName;
    private String filePath;

    public OrderProductDto(OrderProduct orderProduct) {
        this.orderProductId = orderProduct.getId();
        this.salePrice = orderProduct.getSalePrice();
        this.quantity = orderProduct.getQuantity();
        this.point = orderProduct.getPoint();
        this.shippingFee = orderProduct.getShippingFee();
        this.totalPrice = orderProduct.getTotalPrice();
    }

    @QueryProjection
    public OrderProductDto(Long productId, String productName, Long orderProductId, int salePrice, int quantity, Integer point, Integer shippingFee, int totalPrice, String filePath, String fileName) {
        this.productId = productId;
        this.productName = productName;
        this.orderProductId = orderProductId;
        this.salePrice = salePrice;
        this.quantity = quantity;
        this.point = point;
        this.shippingFee = shippingFee;
        this.totalPrice = totalPrice;
        this.filePath = filePath;
        this.fileName = fileName;
    }

    @QueryProjection
    public OrderProductDto(Long orderId, Long orderProductId, String productName, String filePath, String fileName) {
        this.orderId = orderId;
        this.orderProductId = orderProductId;
        this.productName = productName;
        this.filePath = filePath;
        this.fileName = fileName;
    }
}
