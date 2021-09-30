package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.OrderUsePoint;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class OrderPointRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public OrderPointRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public void createOrderPoint(OrderUsePoint orderPoint) {
        em.persist(orderPoint);
    }
}
