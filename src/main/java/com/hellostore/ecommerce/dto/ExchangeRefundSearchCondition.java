package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.enumType.ExchangeRefundStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExchangeRefundSearchCondition {

    private String applicationDateA;
    private String applicationDateB;

    private Long exchangeRefundId;
    private String productName;
    private String username;
    private String name;
    private ExchangeRefundStatus exchangeRefundStatus;
}
