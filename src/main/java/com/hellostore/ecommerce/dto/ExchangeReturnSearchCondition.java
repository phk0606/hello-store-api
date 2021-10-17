package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.enumType.ExchangeReturnStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExchangeReturnSearchCondition {

    private String applicationDateA;
    private String applicationDateB;

    private Long exchangeRefundId;
    private String productName;
    private String username;
    private String name;
    private ExchangeReturnStatus exchangeReturnStatus;
}
