package com.hellostore.ecommerce.entity;

import com.hellostore.ecommerce.enumType.ExchangeRefundReasonType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangeRefund extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_refund_id")
    private Long id;

    @OneToMany(mappedBy = "exchangeRefund", cascade = CascadeType.ALL)
    private List<ExchangeRefundProduct> exchangeRefundProducts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ExchangeRefundReasonType exchangeRefundReasonType;

    private String content;

    @OneToMany(mappedBy = "exchangeRefund", cascade = CascadeType.ALL)
    private List<ExchangeRefundImage> exchangeRefundImages = new ArrayList<>();

    @Builder
    public ExchangeRefund(ExchangeRefundReasonType exchangeRefundReasonType, String content) {
        this.exchangeRefundReasonType = exchangeRefundReasonType;
        this.content = content;
    }
}
