package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ExchangeRefundProductDto;
import com.hellostore.ecommerce.dto.QExchangeRefundProductDto;
import com.hellostore.ecommerce.entity.ExchangeRefundProduct;
import com.hellostore.ecommerce.entity.QExchangeRefundProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QProduct.product;

@Repository
public class ExchangeRefundProductRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ExchangeRefundProductRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public void createExchangeRefundProduct(ExchangeRefundProduct exchangeRefundProduct) {
        em.persist(exchangeRefundProduct);
    }

    public List<ExchangeRefundProductDto> getExchangeRefundProduct(List<Long> exchangeRefundIds) {

        QExchangeRefundProduct exchangeRefundProduct = QExchangeRefundProduct.exchangeRefundProduct;
        List<ExchangeRefundProductDto> exchangeRefundProductDtos = queryFactory.select(
                        new QExchangeRefundProductDto(
                                exchangeRefundProduct.exchangeRefundType,
                                exchangeRefundProduct.exchangeRefund.id,
                                exchangeRefundProduct.id,
                                product.name
                        )
                )
                .from(exchangeRefundProduct)
                .join(product).on(exchangeRefundProduct.orderProduct.product.id.eq(product.id))
                .where(exchangeRefundProduct.exchangeRefund.id.in(exchangeRefundIds))
                .fetch();

        return exchangeRefundProductDtos;
    }
}
