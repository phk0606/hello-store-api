package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ExchangeRefundDto;
import com.hellostore.ecommerce.dto.ExchangeRefundSearchCondition;
import com.hellostore.ecommerce.dto.QExchangeRefundDto;
import com.hellostore.ecommerce.entity.ExchangeRefund;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.hellostore.ecommerce.entity.QExchangeRefund.exchangeRefund;
import static com.hellostore.ecommerce.entity.QExchangeRefundProduct.exchangeRefundProduct;
import static com.hellostore.ecommerce.entity.QProduct.product;
import static com.hellostore.ecommerce.entity.QUser.user;
import static org.springframework.util.ObjectUtils.isEmpty;

@Repository
public class ExchangeRefundRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ExchangeRefundRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public ExchangeRefund createExchangeRefund(ExchangeRefund exchangeRefund) {
        em.persist(exchangeRefund);
        return exchangeRefund;
    }

    public Page<ExchangeRefundDto> getExchangeRefunds (
            ExchangeRefundSearchCondition exchangeRefundSearchCondition, Pageable pageable) {

        QueryResults<ExchangeRefundDto> results = queryFactory.select(
                        new QExchangeRefundDto(
                                exchangeRefund.id,
                                exchangeRefund.createdDate,
                                exchangeRefund.createdBy,
                                user.name,
                                exchangeRefund.exchangeRefundStatus,
                                exchangeRefund.exchangeRefundReasonType,
                                exchangeRefund.content,
                                exchangeRefundProduct.id.count()))
                .from(exchangeRefund)
                .where(
                        applicationDateA(exchangeRefundSearchCondition.getApplicationDateA()),
                        applicationDateB(exchangeRefundSearchCondition.getApplicationDateB()),
                        exchangeRefundIdEq(exchangeRefundSearchCondition.getExchangeRefundId()),
                        usernameEq(exchangeRefundSearchCondition.getUsername()),
                        nameEq(exchangeRefundSearchCondition.getName())
                )
                .join(user).on(user.username.eq(exchangeRefund.createdBy))
                .join(exchangeRefundProduct)
                .on(exchangeRefundProduct.exchangeRefund.id.eq(exchangeRefund.id))
                .orderBy(exchangeRefund.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ExchangeRefundDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression exchangeRefundProductNameContains(String exchangeRefundProductName) {
        return !isEmpty(exchangeRefundProductName)
                ? product.name.contains(exchangeRefundProductName) : null;
    }

    private BooleanExpression exchangeRefundIdEq(Long exchangeRefundId) {
        return !isEmpty(exchangeRefundId)
                ? exchangeRefund.id.eq(exchangeRefundId) : null;
    }

    private BooleanExpression usernameEq(String username) {
        return !isEmpty(username)
                ? user.username.eq(username) : null;
    }

    private BooleanExpression nameEq(String name) {
        return !isEmpty(name)
                ? user.name.eq(name) : null;
    }

    private BooleanExpression applicationDateA(String applicationDateA) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return StringUtils.hasText(applicationDateA)
                ? exchangeRefund.createdDate.goe(LocalDateTime.parse(applicationDateA + " 00:00:00", formatter)) : null;
    }

    private BooleanExpression applicationDateB(String applicationDateB) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return StringUtils.hasText(applicationDateB)
                ? exchangeRefund.createdDate.loe(LocalDateTime.parse(applicationDateB + " 23:59:59", formatter)) : null;
    }
}
