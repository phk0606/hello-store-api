package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.CartProductDto;
import com.hellostore.ecommerce.dto.QCartProductDto;
import com.hellostore.ecommerce.entity.*;
import com.hellostore.ecommerce.enumType.ImageType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QCart.*;
import static com.hellostore.ecommerce.entity.QCartProduct.*;
import static com.hellostore.ecommerce.entity.QCategory.category;
import static com.hellostore.ecommerce.entity.QProduct.*;
import static com.hellostore.ecommerce.entity.QProductImage.*;
import static com.hellostore.ecommerce.entity.QUser.*;
import static org.springframework.util.ObjectUtils.isEmpty;

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

    public List<CartProductDto> getCartProducts(String username, List<Long> cartProductIds) {

        return queryFactory.select(
                new QCartProductDto(cartProduct.cart.id, cartProduct.id, cartProduct.product.id, cartProduct.quantity,
                        cartProduct.firstOptionName, cartProduct.firstOptionValue,
                        cartProduct.secondOptionName, cartProduct.secondOptionValue,
                        product.name, product.salePrice,
                        product.salePrice.multiply(cartProduct.quantity).as("totalPrice"),
                        product.pointType, product.pointPerPrice,
                        product.shippingFeeType, product.eachShippingFee,
                        productImage.filePath, productImage.fileName
                        ))
                .from(cartProduct)
                .join(cart).on(cartProduct.cart.id.eq(cart.id))
                .join(user).on(user.id.eq(cart.user.id))
                .join(product).on(product.id.eq(cartProduct.product.id))
                .leftJoin(productImage).on(productImage.product.id.eq(cartProduct.product.id))
                .on(productImage.imageType.eq(ImageType.LIST))
                .where(
                        usernameEq(username),
                        idIn(cartProductIds)
                )
                .fetch();
    }

    private BooleanExpression usernameEq(String username) {
        return !isEmpty(username)
                ? user.username.eq(username) : null;
    }

    private BooleanExpression idIn(List<Long> cartProductIds) {
        return !isEmpty(cartProductIds)
                ? cartProduct.id.in(cartProductIds) : null;
    }

    public void modifyQuantity(Long cartProductId, int quantity) {
        queryFactory.update(cartProduct)
                .set(cartProduct.quantity, quantity)
                .where(cartProduct.id.eq(cartProductId))
                .execute();
    }

    public void removeCartProducts(List<Long> cartProductIds) {
        queryFactory.delete(cartProduct)
                .where(cartProduct.id.in(cartProductIds))
                .execute();
    }

    public boolean existCartProducts(Long cartId) {
        Integer fetchOne = queryFactory.selectOne()
                .from(cartProduct)
                .where(cartProduct.cart.id.eq(cartId))
                .fetchFirst();
        return fetchOne != null;
    }
}
