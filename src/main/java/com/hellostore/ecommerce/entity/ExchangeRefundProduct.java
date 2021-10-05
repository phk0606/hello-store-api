package com.hellostore.ecommerce.entity;


import com.hellostore.ecommerce.enumType.ExchangeRefundType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangeRefundProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_refund_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exchange_refund_id")
    private ExchangeRefund exchangeRefund;

    @Enumerated(EnumType.STRING)
    private ExchangeRefundType exchangeRefundType;
}
