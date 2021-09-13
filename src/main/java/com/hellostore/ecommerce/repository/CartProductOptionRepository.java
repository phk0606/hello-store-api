package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.CartProductOptionDto;
import com.hellostore.ecommerce.dto.OrderProductOptionDto;
import com.hellostore.ecommerce.dto.QCartProductOptionDto;
import com.hellostore.ecommerce.dto.QOrderProductOptionDto;
import com.hellostore.ecommerce.entity.QCartProductOption;
import com.hellostore.ecommerce.entity.QOrderProductOption;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class CartProductOptionRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public CartProductOptionRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public List<CartProductOptionDto> getCartProductOption(Long cartProductId) {
        QCartProductOption cartProductOption = QCartProductOption.cartProductOption;

        return queryFactory.select(
                        new QCartProductOptionDto(
                                cartProductOption.id, cartProductOption.cartProduct.id,
                                cartProductOption.optionGroupNumber, cartProductOption.optionName,
                                cartProductOption.optionValue))
                .from(cartProductOption)
                .where(cartProductOption.cartProduct.id.eq(cartProductId))
                .fetch();
    }
}
