package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.EventImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.hellostore.ecommerce.entity.QEventImage.eventImage;

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


    public void removeEventImage(Long eventId) {
        queryFactory.delete(eventImage)
                .where(eventImage.id.eq(eventId))
                .execute();
    }
}
