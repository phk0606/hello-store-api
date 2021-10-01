package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.PointHistoryDto;
import com.hellostore.ecommerce.dto.PointHistorySearchCondition;
import com.hellostore.ecommerce.dto.QPointHistoryDto;
import com.hellostore.ecommerce.entity.PointHistory;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QPointHistory.pointHistory;
import static com.hellostore.ecommerce.entity.QUser.user;
import static org.springframework.util.ObjectUtils.isEmpty;

@Repository
public class PointHistoryRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public PointHistoryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public PointHistory createPointHistory(PointHistory pointHistory) {
        em.persist(pointHistory);
        return pointHistory;
    }

    public Integer getUserHavePoint(String username) {

        return queryFactory.select(pointHistory.point.sum())
                .from(pointHistory)
                .where(
                        usernameEq(username)
                ).fetchOne();
    }

    public Page<PointHistoryDto> getPointHistory(PointHistorySearchCondition pointHistorySearchCondition,
                                            Pageable pageable) {

        QueryResults<PointHistoryDto> results
                = queryFactory.select(
                        new QPointHistoryDto(
                                pointHistory.id, pointHistory.pointUseType, pointHistory.pointUseDetailType,
                                pointHistory.point, pointHistory.createdDate
                                ))
                .from(pointHistory)
                .join(user).on(user.id.eq(pointHistory.user.id))
                .where(
                        usernameEq(pointHistorySearchCondition.getUsername())
                )
                .orderBy(pointHistory.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<PointHistoryDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression usernameEq(String username) {
        return !isEmpty(username)
                ? user.username.eq(username) : null;
    }
}
