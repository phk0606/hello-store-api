package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.Order;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class OrderRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public OrderRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }


}
