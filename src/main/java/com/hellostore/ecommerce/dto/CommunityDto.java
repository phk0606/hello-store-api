package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.CommunityReply;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class CommunityDto {

    private Long communityId;
    private String title;
    private String content;
    @Setter
    private List<CommunityReply> replies = new ArrayList<>();

    @Builder
    public CommunityDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @QueryProjection
    public CommunityDto(Long communityId, String title, String content) {
        this.communityId = communityId;
        this.title = title;
        this.content = content;
    }
}
