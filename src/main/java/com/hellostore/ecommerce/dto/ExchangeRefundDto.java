package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.enumType.ExchangeRefundReasonType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class ExchangeRefundDto {

    private Long exchangeRefundId;

    private List<ExchangeRefundProductDto> exchangeRefundProducts = new ArrayList<>();
    private ExchangeRefundReasonType exchangeRefundReasonType;
    private String content;

}
