package com.hellostore.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hellostore.ecommerce.entity.CommunityReply;
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

    @Setter
    private List<CommunityReply> replies = new ArrayList<>();

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
}
