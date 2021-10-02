package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.Faq;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class FaqRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public FaqRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public Faq save(Faq faq) {
        em.persist(faq);
        return faq;
    }
}
