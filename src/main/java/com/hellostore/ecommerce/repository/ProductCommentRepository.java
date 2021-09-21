package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.ProductComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

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
}
