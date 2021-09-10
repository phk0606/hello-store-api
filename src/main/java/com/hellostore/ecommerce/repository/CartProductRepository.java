package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.CartProductDto;
import com.hellostore.ecommerce.dto.QCartProductDto;
import com.hellostore.ecommerce.entity.CartProduct;
import com.hellostore.ecommerce.entity.QCart;
import com.hellostore.ecommerce.entity.QCartProduct;
import com.hellostore.ecommerce.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    public List<CartProductDto> getCartProducts(String username) {
        QCartProduct cartProduct = QCartProduct.cartProduct;
        QCart cart = QCart.cart;
        QUser user = QUser.user;

        return queryFactory.select(
                new QCartProductDto(cartProduct.id, cartProduct.product.id, cartProduct.quantity,
                        cartProduct.firstOptionName, cartProduct.firstOptionValue,
                        cartProduct.secondOptionName, cartProduct.secondOptionValue))
                .from(cartProduct)
                .join(cart).on(cartProduct.cart.id.eq(cart.id))
                .join(user).on(user.id.eq(cart.user.id))
                .where(user.username.eq(username))
                .fetch();
    }
}
