package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.CommunityDto;
import com.hellostore.ecommerce.dto.QCommunityDto;
import com.hellostore.ecommerce.entity.Community;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QCommunity.community;

@Repository
public class CommunityRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public CommunityRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public Community save(Community community) {
        em.persist(community);
        return community;
    }

    public void modifyCommunity(CommunityDto communityDto) {
        queryFactory.update(community)
                .set(community.title, communityDto.getTitle())
                .set(community.content, community.content)
                .where(community.id.eq(communityDto.getCommunityId()))
                .execute();
    }

    public void removeCommunity(CommunityDto communityDto) {
        queryFactory.delete(community)
                .where(community.id.eq(communityDto.getCommunityId()))
                .execute();
    }

    public Page<CommunityDto> getCommunities(Pageable pageable) {
        QueryResults<CommunityDto> results
                = queryFactory.select(
                        new QCommunityDto(
                                community.id,
                                community.title,
                                community.content))
                .from(community)
                .orderBy(community.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<CommunityDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
