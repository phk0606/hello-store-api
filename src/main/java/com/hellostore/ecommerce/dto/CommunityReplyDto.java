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
public class CommunityReplyDto {

    private Long communityId;
    private Long communityReplyId;
    private String content;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @QueryProjection
    public CommunityReplyDto(Long communityId, Long communityReplyId,
                             String content, String createdBy,
                             LocalDateTime createdDate) {
        this.communityId = communityId;
        this.communityReplyId = communityReplyId;
        this.content = content;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }
}
