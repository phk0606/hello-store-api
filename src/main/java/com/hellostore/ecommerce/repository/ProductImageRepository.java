package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.ProductImage;
import com.hellostore.ecommerce.entity.QProductImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.hellostore.ecommerce.entity.QProductImage.*;

@Repository
public class ProductImageRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ProductImageRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public void createProductImage(ProductImage productImage) {

        em.persist(productImage);
    }

    public void removeProductImage(Long productId) {

        queryFactory.delete(productImage)
                .where(productImage.product.id.eq(productId))
                .execute();
    }
}
