package com.hellostore.ecommerce.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private Integer optionGroupNumber;
    private String optionName;
    private String optionValue;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_product_id")
    private OrderProduct orderProduct;
}
