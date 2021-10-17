package com.hellostore.ecommerce.entity;


import com.hellostore.ecommerce.enumType.ExchangeReturnType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangeReturnProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_return_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_product_id")
    private OrderProduct orderProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exchange_return_id")
    private ExchangeReturn exchangeReturn;

    @Enumerated(EnumType.STRING)
    private ExchangeReturnType exchangeReturnType;

    @Builder
    public ExchangeReturnProduct(OrderProduct orderProduct, ExchangeReturn exchangeReturn, ExchangeReturnType exchangeReturnType) {
        this.orderProduct = orderProduct;
        this.exchangeReturn = exchangeReturn;
        this.exchangeReturnType = exchangeReturnType;
    }
}
