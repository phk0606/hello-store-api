package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ProductCommentReplyDto;
import com.hellostore.ecommerce.dto.QProductCommentReplyDto;
import com.hellostore.ecommerce.entity.ProductCommentReply;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.hellostore.ecommerce.entity.QProductCommentReply.productCommentReply;

@Repository
public class ProductCommentReplyRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ProductCommentReplyRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public ProductCommentReply save(ProductCommentReply productCommentReply) {
        em.persist(productCommentReply);
        return productCommentReply;
    }

    public void removeProductCommentReplies(Long productCommentId) {
        queryFactory.delete(productCommentReply)
                .where(productCommentReply.productComment.id.eq(productCommentId))
                .execute();
    }

    public Map<Long, List<ProductCommentReplyDto>> getProductCommentReplies(List<Long> productCommentIds) {

        List<ProductCommentReplyDto> productCommentReplyDtos
                = queryFactory.select(
                        new QProductCommentReplyDto(
                                productCommentReply.id,
                                productCommentReply.productComment.id,
                                productCommentReply.createdBy,
                                productCommentReply.content,
                                productCommentReply.createdDate))
                .from(productCommentReply)
                .where(productCommentReply.productComment.id.in(productCommentIds))
                .orderBy(productCommentReply.id.desc())
                .fetch();

        return productCommentReplyDtos.stream()
                .collect(Collectors.groupingBy(ProductCommentReplyDto::getProductCommentId));
    }

    public List<ProductCommentReplyDto> getProductCommentReplyList(Long productCommentId) {

        return queryFactory.select(
                        new QProductCommentReplyDto(
                                productCommentReply.id,
                                productCommentReply.productComment.id,
                                productCommentReply.createdBy,
                                productCommentReply.content,
                                productCommentReply.createdDate))
                .from(productCommentReply)
                .where(productCommentReply.productComment.id.eq(productCommentId))
                .orderBy(productCommentReply.id.desc())
                .fetch();
    }
}
