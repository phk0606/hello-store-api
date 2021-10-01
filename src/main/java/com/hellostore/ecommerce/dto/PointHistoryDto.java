package com.hellostore.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hellostore.ecommerce.enumType.PointUseDetailType;
import com.hellostore.ecommerce.enumType.PointUseType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class PointHistoryDto {

    private Long pointHistoryId;
    private PointUseType pointUseType;
    private String pointUseTypeValue;
    private PointUseDetailType pointUseDetailType;
    private String pointUseDetailTypeValue;
    private Integer point;
    @Setter
    private Integer pointSum;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdDate;

    @QueryProjection
    public PointHistoryDto(Long pointHistoryId, PointUseType pointUseType, PointUseDetailType pointUseDetailType, Integer point, LocalDateTime createdDate) {
        this.pointHistoryId = pointHistoryId;
        this.pointUseTypeValue = pointUseType.getValue();
        this.pointUseDetailTypeValue = pointUseDetailType.getValue();
        this.point = point;
        this.createdDate = createdDate;
    }
}
