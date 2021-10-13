package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.ProductCommentImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.hellostore.ecommerce.entity.QProductCommentImage.productCommentImage;

@Repository
public class ProductCommentImageRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ProductCommentImageRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public ProductCommentImage getProductCommentImage(Long productCommentImageId) {
        return queryFactory.selectFrom(productCommentImage)
                .where(productCommentImage.id.eq(productCommentImageId))
                .fetchOne();
    }

    public void createProductCommentImage(ProductCommentImage productCommentImage) {

        em.persist(productCommentImage);
    }

    public void removeProductCommentImages(Long productCommentId) {

        queryFactory.delete(productCommentImage)
                .where(productCommentImage.productComment.id.eq(productCommentId))
                .execute();
    }
}
