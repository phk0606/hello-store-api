package com.hellostore.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hellostore.ecommerce.entity.ExchangeRefundImage;
import com.hellostore.ecommerce.enumType.ExchangeRefundReasonType;
import com.hellostore.ecommerce.enumType.ExchangeRefundStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class ExchangeRefundDto {

    private Long exchangeRefundId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    private String username;
    private String name;
    private ExchangeRefundStatus exchangeRefundStatus;
    private String exchangeRefundStatusValue;

    @Setter
    private List<ExchangeRefundProductDto> exchangeRefundProducts = new ArrayList<>();

    @Setter
    private List<ExchangeRefundImageDto> exchangeRefundImages = new ArrayList<>();
    private ExchangeRefundReasonType exchangeRefundReasonType;
    private String content;

    private Long exchangeRefundProductCount;

    @QueryProjection
    public ExchangeRefundDto(Long exchangeRefundId, LocalDateTime createdDate, String username, String name, ExchangeRefundStatus exchangeRefundStatus, ExchangeRefundReasonType exchangeRefundReasonType, String content, Long exchangeRefundProductCount) {
        this.exchangeRefundId = exchangeRefundId;
        this.createdDate = createdDate;
        this.username = username;
        this.name = name;
        this.exchangeRefundStatus = exchangeRefundStatus;
        this.exchangeRefundStatusValue = exchangeRefundStatus.getValue();
        this.exchangeRefundReasonType = exchangeRefundReasonType;
        this.content = content;
        this.exchangeRefundProductCount = exchangeRefundProductCount;
    }

    @QueryProjection
    public ExchangeRefundDto(Long exchangeRefundId, LocalDateTime createdDate, String username, String name, ExchangeRefundStatus exchangeRefundStatus, ExchangeRefundReasonType exchangeRefundReasonType, String content) {
        this.exchangeRefundId = exchangeRefundId;
        this.createdDate = createdDate;
        this.username = username;
        this.name = name;
        this.exchangeRefundStatus = exchangeRefundStatus;
        this.exchangeRefundStatusValue = exchangeRefundStatus.getValue();
        this.exchangeRefundReasonType = exchangeRefundReasonType;
        this.content = content;
    }
}
