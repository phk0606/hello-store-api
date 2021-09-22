package com.hellostore.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class NoticeDto {

    private Long noticeId;
    private String title;
    private String content;
    private boolean important;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @QueryProjection
    public NoticeDto(Long noticeId, String title,
                     String content, boolean important, LocalDateTime createdDate) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.important = important;
        this.createdDate = createdDate;
    }
}
