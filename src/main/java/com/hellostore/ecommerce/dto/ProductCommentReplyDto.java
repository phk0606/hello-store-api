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
public class ProductCommentReplyDto {

    private Long productCommentReplyId;
    private Long productCommentId;
    private Long userId;
    private String username;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @QueryProjection
    public ProductCommentReplyDto(Long productCommentReplyId, Long productCommentId,
                                  String username, String content, LocalDateTime createdDate) {
        this.productCommentReplyId = productCommentReplyId;
        this.productCommentId = productCommentId;
        this.username = username;
        this.content = content;
        this.createdDate = createdDate;
    }
}
