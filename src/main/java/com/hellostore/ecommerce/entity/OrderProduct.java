package com.hellostore.ecommerce.entity;

import com.hellostore.ecommerce.dto.OrderProductDto;
import lombok.*;

import javax.persistence.*;

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
    
    private int salePrice;
    private int orderQuantity;
    private int point;
    private int orderShippingFee;
    private int totalPrice;

    public static OrderProduct createOrderProduct(Product product, OrderProductDto orderProductDto) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setSalePrice(orderProductDto.getSalePrice());
        orderProduct.setOrderQuantity(orderProductDto.getOrderQuantity());
        orderProduct.setPoint(orderProductDto.getPoint());
        orderProduct.setOrderShippingFee(orderProduct.getOrderShippingFee());
        orderProduct.setTotalPrice(orderProductDto.getTotalPrice());

        product.removeStock(orderProductDto.getOrderQuantity());
        return orderProduct;
    }
    
    public void cancel() {
        getProduct().addStock(orderQuantity);
    } 
    
    public int getTotalPrice() {
        return getSalePrice() * getOrderQuantity();
    }

}
