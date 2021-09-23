package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.CommunityDto;
import com.hellostore.ecommerce.dto.CommunitySearchCondition;
import com.hellostore.ecommerce.dto.QCommunityDto;
import com.hellostore.ecommerce.entity.Community;
import com.hellostore.ecommerce.entity.QCommunityReply;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QCommunity.community;
import static com.hellostore.ecommerce.entity.QCommunityReply.*;
import static org.springframework.util.ObjectUtils.isEmpty;

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
                .set(community.content, communityDto.getContent())
                .where(community.id.eq(communityDto.getCommunityId()))
                .execute();
    }

    public void removeCommunity(CommunityDto communityDto) {
        queryFactory.delete(community)
                .where(community.id.eq(communityDto.getCommunityId()))
                .execute();
    }

    public Page<CommunityDto> getCommunities(
            CommunitySearchCondition communitySearchCondition,
            Pageable pageable) {

        QueryResults<CommunityDto> results
                = queryFactory.select(
                        new QCommunityDto(
                                community.id,
                                community.title,
                                community.content,
                                community.createdBy,
                                community.createdDate))
                .from(community)
                .where(
                        communityTitleContains(communitySearchCondition.getTitle()),
                        communityContentContains(communitySearchCondition.getContent())
                )
                .orderBy(community.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<CommunityDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression communityTitleContains(String title) {
        return !isEmpty(title)
                ? community.title.contains(title) : null;
    }

    private BooleanExpression communityContentContains(String content) {
        return !isEmpty(content)
                ? community.content.contains(content) : null;
    }

    public CommunityDto getCommunity(Long communityId) {
        return queryFactory.select(new QCommunityDto(community.id,
                        community.title, community.content,
                        community.createdBy, community.createdDate,
                        communityReply.id.count()))
                .from(community)
                .leftJoin(communityReply)
                .on(communityReply.community.id.eq(community.id))
                .where(community.id.eq(communityId))
                .fetchOne();
    }

    public Community getCommunityOne(Long communityId) {

        return queryFactory.selectFrom(community)
                .where(community.id.eq(communityId))
                .fetchOne();
    }
}
