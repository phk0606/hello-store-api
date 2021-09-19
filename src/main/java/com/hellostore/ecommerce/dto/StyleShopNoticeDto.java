package com.hellostore.ecommerce.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class StyleShopNoticeDto {

    private Long id;
    private Long categoryId;
    private String content;

    @QueryProjection
    public StyleShopNoticeDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
