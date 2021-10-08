package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.EventImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class EventImageRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public EventImageRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public void createEventImage(EventImage eventImage) {

        em.persist(eventImage);
    }


}
