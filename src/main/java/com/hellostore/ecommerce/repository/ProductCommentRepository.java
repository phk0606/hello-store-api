package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ProductCommentDto;
import com.hellostore.ecommerce.dto.ProductCommentSearchCondition;
import com.hellostore.ecommerce.dto.QProductCommentDto;
import com.hellostore.ecommerce.entity.ProductComment;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QCategory.category;
import static com.hellostore.ecommerce.entity.QCategoryProduct.categoryProduct;
import static com.hellostore.ecommerce.entity.QProduct.product;
import static com.hellostore.ecommerce.entity.QProductComment.productComment;
import static com.hellostore.ecommerce.entity.QProductCommentImage.productCommentImage;
import static com.hellostore.ecommerce.entity.QProductCommentReply.productCommentReply;
import static com.hellostore.ecommerce.entity.QUser.user;
import static org.springframework.util.ObjectUtils.isEmpty;

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

    public Page<ProductCommentDto> getProductComments(
            ProductCommentSearchCondition productCommentSearchCondition, Pageable pageable) {

        QueryResults<ProductCommentDto> results =
                queryFactory.select(
                        new QProductCommentDto(
                                productComment.id, user.username,
                                productComment.content, productComment.grade,
                                productCommentImage.fileName, productComment.createdDate,
                                productCommentReply.id.count(),
                                product.id, product.name, category.name
                        )
                )
                .from(productComment)
                .join(product).on(product.id.eq(productComment.product.id))
                        .join(categoryProduct)
                        .on(categoryProduct.product.id.eq(productComment.product.id))
                        .join(category)
                        .on(category.id.eq(categoryProduct.category.id))
                .join(user).on(user.username.eq(productComment.createdBy))
                .leftJoin(productCommentReply)
                .on(productCommentReply.productComment.id.eq(productComment.id))
                .leftJoin(productCommentImage)
                .on(productCommentImage.productComment.id.eq(productComment.id))
                .where(
                        productIdEq(productCommentSearchCondition.getProductId()),
                        usernameEq(productCommentSearchCondition.getUsername()),
                        productNameContains(productCommentSearchCondition.getProductName())
                )
                .orderBy(productComment.id.desc())
                .groupBy(productComment.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ProductCommentDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression productIdEq(Long productId) {
        return !isEmpty(productId)
                ? product.id.eq(productId) : null;
    }

    private BooleanExpression usernameEq(String username) {
        return !isEmpty(username)
                ? user.username.eq(username) : null;
    }

    private BooleanExpression productNameContains(String productName) {
        return !isEmpty(productName)
                ? product.name.contains(productName) : null;
    }
}
