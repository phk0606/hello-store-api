package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ExchangeReturnDto;
import com.hellostore.ecommerce.dto.ExchangeReturnSearchCondition;
import com.hellostore.ecommerce.dto.QExchangeReturnDto;
import com.hellostore.ecommerce.entity.ExchangeReturn;
import com.hellostore.ecommerce.entity.QOrderProduct;
import com.hellostore.ecommerce.entity.QProduct;
import com.hellostore.ecommerce.enumType.ExchangeReturnStatus;
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

import static com.hellostore.ecommerce.entity.QExchangeReturn.exchangeReturn;
import static com.hellostore.ecommerce.entity.QExchangeReturnProduct.exchangeReturnProduct;
import static com.hellostore.ecommerce.entity.QUser.user;
import static org.springframework.util.ObjectUtils.isEmpty;

@Repository
public class ExchangeReturnRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ExchangeReturnRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public ExchangeReturn createExchangeReturn(ExchangeReturn exchangeReturn) {
        em.persist(exchangeReturn);
        return exchangeReturn;
    }

    public Page<ExchangeReturnDto> getExchangeReturns (
            ExchangeReturnSearchCondition exchangeReturnSearchCondition, Pageable pageable) {

        QueryResults<ExchangeReturnDto> results = queryFactory.select(
                        new QExchangeReturnDto(
                                exchangeReturn.id,
                                exchangeReturn.order.id,
                                exchangeReturn.createdDate,
                                exchangeReturn.createdBy,
                                user.name,
                                user.id,
                                exchangeReturn.exchangeReturnStatus,
                                exchangeReturn.exchangeReturnReasonType,
                                exchangeReturn.content,
                                exchangeReturnProduct.id.count(),
                                exchangeReturn.memo))
                .from(exchangeReturn)
                .where(
                        applicationDateA(exchangeReturnSearchCondition.getApplicationDateA()),
                        applicationDateB(exchangeReturnSearchCondition.getApplicationDateB()),
                        exchangeReturnIdEq(exchangeReturnSearchCondition.getExchangeRefundId()),
                        usernameEq(exchangeReturnSearchCondition.getUsername()),
                        nameEq(exchangeReturnSearchCondition.getName()),
                        exchangeReturnStatusEq(exchangeReturnSearchCondition.getExchangeReturnStatus())
                )
                .join(user).on(user.username.eq(exchangeReturn.createdBy))
                .join(exchangeReturnProduct)
                .on(exchangeReturnProduct.exchangeReturn.id.eq(exchangeReturn.id))
                .groupBy(exchangeReturn.id)
                .orderBy(exchangeReturn.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ExchangeReturnDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression exchangeReturnIdEq(Long exchangeReturnId) {
        return !isEmpty(exchangeReturnId)
                ? exchangeReturn.id.eq(exchangeReturnId) : null;
    }

    private BooleanExpression usernameEq(String username) {
        return !isEmpty(username)
                ? user.username.eq(username) : null;
    }

    private BooleanExpression nameEq(String name) {
        return !isEmpty(name)
                ? user.name.eq(name) : null;
    }

    private BooleanExpression exchangeReturnStatusEq(ExchangeReturnStatus exchangeReturnStatus) {
        return !isEmpty(exchangeReturnStatus)
                ? exchangeReturn.exchangeReturnStatus.eq(exchangeReturnStatus) : null;
    }

    private BooleanExpression applicationDateA(String applicationDateA) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return StringUtils.hasText(applicationDateA)
                ? exchangeReturn.createdDate.goe(LocalDateTime.parse(applicationDateA + " 00:00:00", formatter)) : null;
    }

    private BooleanExpression applicationDateB(String applicationDateB) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return StringUtils.hasText(applicationDateB)
                ? exchangeReturn.createdDate.loe(LocalDateTime.parse(applicationDateB + " 23:59:59", formatter)) : null;
    }

    public ExchangeReturnDto getExchangeReturn(Long exchangeReturnId) {

        return queryFactory.select(
                new QExchangeReturnDto(
                        exchangeReturn.id,
                        exchangeReturn.order.id,
                        exchangeReturn.createdDate,
                        exchangeReturn.createdBy,
                        user.name,
                        user.id,
                        exchangeReturn.exchangeReturnStatus,
                        exchangeReturn.exchangeReturnReasonType,
                        exchangeReturn.content,
                        exchangeReturn.memo
                        )
                )
                .from(exchangeReturn)
                .join(user).on(user.username.eq(exchangeReturn.createdBy))
                .where(exchangeReturn.id.eq(exchangeReturnId))
                .fetchOne();
    }

    public void modifyExchangeReturnStatus(List<Long> exchangeReturnIds, ExchangeReturnStatus exchangeReturnStatus) {
        queryFactory.update(exchangeReturn)
                .set(exchangeReturn.exchangeReturnStatus, exchangeReturnStatus)
                .where(exchangeReturn.id.in(exchangeReturnIds))
                .execute();
    }

    public void modifyExchangeReturn(ExchangeReturnDto exchangeReturnDto) {
        queryFactory.update(exchangeReturn)
                .set(exchangeReturn.exchangeReturnStatus, exchangeReturnDto.getExchangeReturnStatus())
                .set(exchangeReturn.exchangeReturnReasonType, exchangeReturnDto.getExchangeReturnReasonType())
                .set(exchangeReturn.content, exchangeReturnDto.getContent())
                .set(exchangeReturn.memo, exchangeReturnDto.getMemo())
                .where(exchangeReturn.id.eq(exchangeReturnDto.getExchangeReturnId()))
                .execute();
    }

    public void removeExchangeReturn(ExchangeReturnDto exchangeReturnDto) {

        queryFactory.delete(exchangeReturn)
                .where(exchangeReturn.id.eq(exchangeReturnDto.getExchangeReturnId()))
                .execute();
    }
}
