package com.hellostore.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hellostore.ecommerce.enumType.ManToManQuestionType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class ManToManQuestionDto {

    private Long manToManQuestionId;
    private String manToManQuestionTitle;
    private String manToManQuestionContent;

    private ManToManQuestionType manToManQuestionType;
    private String manToManQuestionTypeValue;

    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdDate;

    private Long manToManAnswerId;

    @QueryProjection
    public ManToManQuestionDto(Long manToManQuestionId, String manToManQuestionTitle, String manToManQuestionContent, ManToManQuestionType manToManQuestionType, String createdBy, LocalDateTime createdDate, Long manToManAnswerId) {
        this.manToManQuestionId = manToManQuestionId;
        this.manToManQuestionTitle = manToManQuestionTitle;
        this.manToManQuestionContent = manToManQuestionContent;
        this.manToManQuestionType = manToManQuestionType;
        this.manToManQuestionTypeValue = manToManQuestionType.getValue();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.manToManAnswerId = manToManAnswerId;
    }
}
