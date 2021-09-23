package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.CommunityDto;
import com.hellostore.ecommerce.dto.CommunitySearchCondition;
import com.hellostore.ecommerce.service.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
@Slf4j
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping("/createCommunity")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void createNotice(@RequestBody CommunityDto communityDto) {
        log.debug("communityDto: {}", communityDto);
        communityService.createCommunity(communityDto);
    }

    @PutMapping("/modifyCommunity")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void modifyCommunity(@RequestBody CommunityDto communityDto) {
        communityService.modifyCommunity(communityDto);
    }

    @DeleteMapping("/removeCommunity")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void removeCommunity(@RequestBody CommunityDto communityDto) {
        communityService.removeCommunity(communityDto);
    }

    @GetMapping("/getCommunities")
    public Page<CommunityDto> getCommunities(CommunitySearchCondition communitySearchCondition,
                                          Pageable pageable) {
        log.debug("communitySearchCondition: {}", communitySearchCondition);
        return communityService.getCommunities(communitySearchCondition, pageable);
    }

    @GetMapping("/getCommunity")
    public CommunityDto getCommunity(@RequestParam Long communityId) {
        return communityService.getCommunity(communityId);
    }
}
