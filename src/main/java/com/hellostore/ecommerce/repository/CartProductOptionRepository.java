package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.CartProductOptionDto;
import com.hellostore.ecommerce.dto.QCartProductOptionDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QCartProductOption.cartProductOption;

@Repository
public class CartProductOptionRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public CartProductOptionRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public void removeCartProductOptions(List<Long> cartProductIds) {
        queryFactory.delete(cartProductOption)
                .where(cartProductOption.cartProduct.id.in(cartProductIds))
                .execute();
    }

    public List<CartProductOptionDto> getCartProductOption(Long cartProductId) {

        return queryFactory.select(
                        new QCartProductOptionDto(
                                cartProductOption.id, cartProductOption.cartProduct.id,
                                cartProductOption.optionId,
                                cartProductOption.optionGroupNumber, cartProductOption.optionName,
                                cartProductOption.optionValue))
                .from(cartProductOption)
                .where(cartProductOption.cartProduct.id.eq(cartProductId))
                .fetch();
    }
}
