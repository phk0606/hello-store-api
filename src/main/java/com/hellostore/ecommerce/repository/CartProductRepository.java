package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.CartProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CartProductRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public CartProductRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public void save(CartProduct cartProduct) {
        em.persist(cartProduct);
    }
}
