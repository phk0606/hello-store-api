package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.CommunityDto;
import com.hellostore.ecommerce.dto.CommunityReplyDto;
import com.hellostore.ecommerce.dto.CommunitySearchCondition;
import com.hellostore.ecommerce.entity.Community;
import com.hellostore.ecommerce.repository.CommunityReplyRepository;
import com.hellostore.ecommerce.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityReplyRepository communityReplyRepository;

    @Transactional
    public void createCommunity(CommunityDto communityDto) {

        Community community = Community.builder().title(communityDto.getTitle())
                .content(communityDto.getContent())
                .build();

        communityRepository.save(community);
    }

    public Page<CommunityDto> getCommunities(
            CommunitySearchCondition communitySearchCondition, Pageable pageable) {
        return communityRepository.getCommunities(communitySearchCondition, pageable);
    }

    public CommunityDto getCommunity(Long communityId) {
        CommunityDto community = communityRepository.getCommunity(communityId);
        List<CommunityReplyDto> communityReplies
                = communityReplyRepository.getCommunityReplies(communityId);
        community.setReplies(communityReplies);

        return community;
    }

    @Transactional
    public void modifyCommunity(CommunityDto communityDto) {
        communityRepository.modifyCommunity(communityDto);
    }

    @Transactional
    public void removeCommunity(CommunityDto communityDto) {
        communityRepository.removeCommunity(communityDto);
    }
}
