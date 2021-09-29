package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.PointHistory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class PointRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public PointRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public PointHistory createPointHistory(PointHistory pointHistory) {
        em.persist(pointHistory);
        return pointHistory;
    }
}
