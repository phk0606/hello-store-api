package com.hellostore.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class CommunityDto {

    private Long communityId;
    private String title;
    private String content;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    private Long replyCount;

    @Setter
    private List<CommunityReplyDto> replies = new ArrayList<>();

    @Builder
    public CommunityDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @QueryProjection
    public CommunityDto(Long communityId, String title,
                        String content,
                        String createdBy, LocalDateTime createdDate) {
        this.communityId = communityId;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    @QueryProjection
    public CommunityDto(Long communityId, String title,
                        String createdBy, LocalDateTime createdDate) {
        this.communityId = communityId;
        this.title = title;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    @QueryProjection
    public CommunityDto(Long communityId, String title,
                        String content,
                        String createdBy, LocalDateTime createdDate,
                        Long replyCount) {
        this.communityId = communityId;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.replyCount = replyCount;
    }
}
