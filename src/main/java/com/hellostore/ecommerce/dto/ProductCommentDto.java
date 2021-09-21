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
public class ProductCommentDto {

    private Long orderProductId;
    private Long productCommentId;
    private Long orderId;
    private String username;
    private Long productId;
    private String content;
    private int grade;

    private String fileName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    private Long replyCount;

    @QueryProjection
    public ProductCommentDto(Long productCommentId, String username,
                             String content, int grade, String fileName,
                             LocalDateTime createdDate,
                             Long replyCount) {
        this.productCommentId = productCommentId;
        this.username = username;
        this.content = content;
        this.grade = grade;
        this.fileName = fileName;
        this.createdDate = createdDate;
        this.replyCount = replyCount;
    }
}
