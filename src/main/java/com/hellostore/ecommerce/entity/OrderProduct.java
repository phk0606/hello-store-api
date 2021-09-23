package com.hellostore.ecommerce.entity;

import com.hellostore.ecommerce.dto.OrderProductDto;
import com.hellostore.ecommerce.dto.OrderProductOptionDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToMany(mappedBy = "orderProduct", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProductOption> orderProductOptions = new ArrayList<>();

    private int salePrice;
    private int quantity;
    private Integer point;
    private Integer shippingFee;
    private int totalPrice;

    public static OrderProduct createOrderProduct(Product product, OrderProductDto orderProductDto) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setSalePrice(orderProductDto.getSalePrice());
        orderProduct.setQuantity(orderProductDto.getQuantity());
        orderProduct.setPoint(orderProductDto.getPoint());
        orderProduct.setShippingFee(orderProductDto.getShippingFee());
        orderProduct.setTotalPrice(orderProductDto.getTotalPrice());

        List<OrderProductOptionDto> orderProductOptionDtos = orderProductDto.getProductOptions();
        List<OrderProductOption> orderProductOptions = new ArrayList<>();
        for (OrderProductOptionDto orderProductOption : orderProductOptionDtos) {
            orderProductOptions.add(
                    OrderProductOption.builder()
                            .orderProduct(orderProduct)
                            .optionGroupNumber(orderProductOption.getOptionGroupNumber())
                            .optionName(orderProductOption.getOptionName())
                            .optionValue(orderProductOption.getOptionValue())
                            .build());
        }
        orderProduct.setOrderProductOptions(orderProductOptions);

        product.removeStock(orderProductDto.getQuantity());
        return orderProduct;
    }
    
    public void cancel() {
        getProduct().addStock(quantity);
    } 
    
    public int getTotalPrice() {
        return getSalePrice() * getQuantity();
    }

}
