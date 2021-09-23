package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.CommunityReplyDto;
import com.hellostore.ecommerce.entity.Community;
import com.hellostore.ecommerce.entity.CommunityReply;
import com.hellostore.ecommerce.repository.CommunityReplyRepository;
import com.hellostore.ecommerce.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CommunityReplyService {

    private final CommunityRepository communityRepository;
    private final CommunityReplyRepository communityReplyRepository;

    @Transactional
    public void createCommunityReply(CommunityReplyDto communityReplyDto) {

        Community community
                = communityRepository
                .getCommunityOne(communityReplyDto.getCommunityId());

        CommunityReply communityReply = CommunityReply.builder()
                .content(communityReplyDto.getContent())
                .community(community).build();

        communityReplyRepository.save(communityReply);
    }

    @Transactional
    public void modifyCommunityReply(CommunityReplyDto communityReplyDto) {
        communityReplyRepository.modifyCommunityReply(communityReplyDto);
    }

    @Transactional
    public void removeCommunityReply(CommunityReplyDto communityReplyDto) {
        communityReplyRepository.removeCommunityReply(communityReplyDto);
    }
}
