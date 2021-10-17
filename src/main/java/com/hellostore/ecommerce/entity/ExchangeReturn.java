package com.hellostore.ecommerce.entity;

import com.hellostore.ecommerce.enumType.ExchangeReturnReasonType;
import com.hellostore.ecommerce.enumType.ExchangeReturnStatus;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangeReturn extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_return_id")
    private Long id;

    @OneToMany(mappedBy = "exchangeReturn", cascade = CascadeType.ALL)
    private List<ExchangeReturnProduct> exchangeRefundProducts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ExchangeReturnReasonType exchangeReturnReasonType;

    @Enumerated(EnumType.STRING)
    private ExchangeReturnStatus exchangeReturnStatus;

    private String content;

    @OneToMany(mappedBy = "exchangeReturn", cascade = CascadeType.ALL)
    private List<ExchangeReturnImage> exchangeReturnImages = new ArrayList<>();

    @Builder
    public ExchangeReturn(ExchangeReturnReasonType exchangeReturnReasonType, ExchangeReturnStatus exchangeReturnStatus, String content) {
        this.exchangeReturnReasonType = exchangeReturnReasonType;
        this.exchangeReturnStatus = exchangeReturnStatus;
        this.content = content;
    }
}
