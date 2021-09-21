package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ProductCommentDto;
import com.hellostore.ecommerce.dto.QProductCommentDto;
import com.hellostore.ecommerce.dto.ShopProductDto;
import com.hellostore.ecommerce.entity.*;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QOrderProduct.*;
import static com.hellostore.ecommerce.entity.QProduct.*;
import static com.hellostore.ecommerce.entity.QProductComment.*;
import static com.hellostore.ecommerce.entity.QProductCommentImage.*;
import static com.hellostore.ecommerce.entity.QProductCommentReply.*;
import static com.hellostore.ecommerce.entity.QUser.*;

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
                .join(user).on(user.id.eq(productComment.user.id))
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
