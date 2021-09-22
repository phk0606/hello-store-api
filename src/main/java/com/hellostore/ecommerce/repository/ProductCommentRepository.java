package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ProductCommentDto;
import com.hellostore.ecommerce.dto.QProductCommentDto;
import com.hellostore.ecommerce.entity.ProductComment;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QOrderProduct.orderProduct;
import static com.hellostore.ecommerce.entity.QProductComment.productComment;
import static com.hellostore.ecommerce.entity.QProductCommentImage.productCommentImage;
import static com.hellostore.ecommerce.entity.QProductCommentReply.productCommentReply;
import static com.hellostore.ecommerce.entity.QUser.user;

@Repository
public class ProductCommentRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ProductCommentRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public ProductComment save(ProductComment productComment) {
        em.persist(productComment);
        return productComment;
    }

    public void modifyProductComment(ProductCommentDto productCommentDto) {
        queryFactory.update(productComment)
                .set(productComment.content, productCommentDto.getContent())
                .where(productComment.id.eq(productCommentDto.getProductCommentId()))
                .execute();
    }

    public void removeProductComment(Long productCommentId) {
        queryFactory.delete(productComment)
                .where(productComment.id.eq(productCommentId))
                .execute();
    }

    public ProductComment getProductCommentById(Long productCommentId) {
        return queryFactory.selectFrom(productComment)
                .where(productComment.id.eq(productCommentId))
                .fetchOne();
    }

    public Page<ProductCommentDto> getProductComments(Long productId, Pageable pageable) {

        QueryResults<ProductCommentDto> results =
                queryFactory.select(
                        new QProductCommentDto(
                                productComment.id, user.username,
                                productComment.content, productComment.grade,
                                productCommentImage.fileName, productComment.createdDate,
                                productCommentReply.id.count()
                        )
                )
                .from(productComment)
                .join(orderProduct).on(orderProduct.id.eq(productComment.orderProduct.id))
                .join(user).on(user.username.eq(productComment.createdBy))
                .leftJoin(productCommentReply)
                .on(productCommentReply.productComment.id.eq(productComment.id))
                .leftJoin(productCommentImage)
                .on(productCommentImage.productComment.id.eq(productComment.id))
                .where(orderProduct.product.id.eq(productId))
                .orderBy(productComment.id.desc())
                .groupBy(productComment.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ProductCommentDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
