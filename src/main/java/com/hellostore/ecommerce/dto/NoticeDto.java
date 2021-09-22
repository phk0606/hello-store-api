package com.hellostore.ecommerce.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class NoticeDto {

    private Long noticeId;
    private String title;
    private String content;

    @QueryProjection
    public NoticeDto(Long noticeId, String title, String content) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
    }
}
