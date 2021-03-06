package com.hellostore.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hellostore.ecommerce.enumType.ExchangeReturnReasonType;
import com.hellostore.ecommerce.enumType.ExchangeReturnStatus;
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
public class ExchangeReturnDto {

    private Long exchangeReturnId;
    private Long orderId;

    private List<Long> exchangeReturnIds;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    private String username;
    private String name;
    private Long userNo;
    private ExchangeReturnStatus exchangeReturnStatus;
    private String exchangeReturnStatusValue;

    @Setter
    private List<ExchangeReturnProductDto> exchangeReturnProducts = new ArrayList<>();

    @Setter
    private List<ExchangeReturnImageDto> exchangeReturnImages = new ArrayList<>();
    private ExchangeReturnReasonType exchangeReturnReasonType;
    private String content;

    private Long exchangeReturnProductCount;
    private String memo;

    @QueryProjection
    public ExchangeReturnDto(Long exchangeReturnId, Long orderId, LocalDateTime createdDate, String username, String name, Long userNo, ExchangeReturnStatus exchangeReturnStatus, ExchangeReturnReasonType exchangeReturnReasonType, String content, Long exchangeReturnProductCount, String memo) {
        this.exchangeReturnId = exchangeReturnId;
        this.orderId = orderId;
        this.createdDate = createdDate;
        this.username = username;
        this.name = name;
        this.userNo = userNo;
        this.exchangeReturnStatus = exchangeReturnStatus;
        this.exchangeReturnStatusValue = exchangeReturnStatus.getValue();
        this.exchangeReturnReasonType = exchangeReturnReasonType;
        this.content = content;
        this.exchangeReturnProductCount = exchangeReturnProductCount;
        this.memo = memo;
    }

    @QueryProjection
    public ExchangeReturnDto(Long exchangeReturnId, Long orderId, LocalDateTime createdDate, String username, String name, Long userNo, ExchangeReturnStatus exchangeReturnStatus, ExchangeReturnReasonType exchangeReturnReasonType, String content, String memo) {
        this.exchangeReturnId = exchangeReturnId;
        this.orderId = orderId;
        this.createdDate = createdDate;
        this.username = username;
        this.name = name;
        this.userNo = userNo;
        this.exchangeReturnStatus = exchangeReturnStatus;
        this.exchangeReturnStatusValue = exchangeReturnStatus.getValue();
        this.exchangeReturnReasonType = exchangeReturnReasonType;
        this.content = content;
        this.memo = memo;
    }
}
