package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.Cart;
import com.hellostore.ecommerce.entity.QCart;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.hellostore.ecommerce.entity.QCart.*;

@Repository
public class CartRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public CartRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public Cart save(Cart cart) {
        em.persist(cart);
        return cart;
    }

    public Cart getCart(Long userNo) {

        return queryFactory.selectFrom(cart)
                .where(cart.user.id.eq(userNo))
                .fetchOne();
    }

    public void removeCart(Long cartId) {
        queryFactory.delete(cart)
                .where(cart.id.eq(cartId))
                .execute();
    }
}
