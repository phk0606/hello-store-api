package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.CommunityReplyDto;
import com.hellostore.ecommerce.dto.QCommunityReplyDto;
import com.hellostore.ecommerce.entity.CommunityReply;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.hellostore.ecommerce.entity.QCommunityReply.communityReply;

@Repository
public class CommunityReplyRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public CommunityReplyRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public CommunityReply save(CommunityReply communityReply) {
        em.persist(communityReply);
        return communityReply;
    }

    public List<CommunityReplyDto> getCommunityReplies(Long communityId) {

        return queryFactory.select(
                new QCommunityReplyDto(
                        communityReply.community.id,
                        communityReply.id,
                        communityReply.content,
                        communityReply.createdBy, communityReply.createdDate))
                .from(communityReply)
                .where(communityReply.community.id.eq(communityId))
                .fetch();
    }

    public void modifyCommunityReply(CommunityReplyDto communityReplyDto) {
        queryFactory.update(communityReply)
                .set(communityReply.content, communityReplyDto.getContent())
                .where(communityReply.id.eq(communityReplyDto.getCommunityReplyId()))
                .execute();
    }

    public void removeCommunityReply(CommunityReplyDto communityReplyDto) {
        queryFactory.delete(communityReply)
                .where(communityReply.id.eq(communityReplyDto.getCommunityReplyId()))
                .execute();
    }
}
