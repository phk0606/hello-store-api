package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_option_id")
    private Long id;

    private Long optionId;
    private Integer optionGroupNumber;
    private String optionName;
    private String optionValue;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_product_id")
    private OrderProduct orderProduct;

    @Builder
    public OrderProductOption(OrderProduct orderProduct, Long optionId, Integer optionGroupNumber, String optionName, String optionValue) {
        this.orderProduct = orderProduct;
        this.optionId = optionId;
        this.optionGroupNumber = optionGroupNumber;
        this.optionName = optionName;
        this.optionValue = optionValue;
    }
}
