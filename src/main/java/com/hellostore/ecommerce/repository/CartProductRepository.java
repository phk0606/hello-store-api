package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.CartProductDto;
import com.hellostore.ecommerce.dto.QCartProductDto;
import com.hellostore.ecommerce.entity.*;
import com.hellostore.ecommerce.enumType.ImageType;
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
        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;

        return queryFactory.select(
                new QCartProductDto(cartProduct.id, cartProduct.product.id, cartProduct.quantity,
                        cartProduct.firstOptionName, cartProduct.firstOptionValue,
                        cartProduct.secondOptionName, cartProduct.secondOptionValue,
                        product.name, product.salePrice,
                        product.salePrice.multiply(cartProduct.quantity).as("totalPrice"),
                        productImage.filePath, productImage.fileName
                        ))
                .from(cartProduct)
                .join(cart).on(cartProduct.cart.id.eq(cart.id))
                .join(user).on(user.id.eq(cart.user.id))
                .join(product).on(product.id.eq(cartProduct.product.id))
                .leftJoin(productImage).on(productImage.product.id.eq(cartProduct.product.id))
                .on(productImage.imageType.eq(ImageType.LIST))
                .where(user.username.eq(username))
                .fetch();
    }
}
