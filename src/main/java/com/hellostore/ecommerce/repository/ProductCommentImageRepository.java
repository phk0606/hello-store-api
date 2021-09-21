package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.ProductCommentImage;
import com.hellostore.ecommerce.entity.ProductImage;
import com.hellostore.ecommerce.entity.QProductCommentImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QProductImage.productImage;

@Repository
public class ProductCommentImageRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ProductCommentImageRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public void createProductCommentImage(ProductCommentImage productCommentImage) {

        em.persist(productCommentImage);
    }

    public void removeProductCommentImages(Long productCommentId) {

        QProductCommentImage productCommentImage = QProductCommentImage.productCommentImage;
        queryFactory.delete(productCommentImage)
                .where(productCommentImage.productComment.id.eq(productCommentId))
                .execute();
    }
}
