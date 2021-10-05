package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.ExchangeRefundProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ExchangeRefundProductRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ExchangeRefundProductRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public void createExchangeRefundProduct(ExchangeRefundProduct exchangeRefundProduct) {
        em.persist(exchangeRefundProduct);
    }
}
