package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.QUserDto;
import com.hellostore.ecommerce.dto.ShopProductDto;
import com.hellostore.ecommerce.dto.UserDto;
import com.hellostore.ecommerce.dto.UserSearchCondition;
import com.hellostore.ecommerce.entity.QOrder;
import com.hellostore.ecommerce.entity.QUser;
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

import static com.hellostore.ecommerce.entity.QOrder.*;
import static com.hellostore.ecommerce.entity.QProduct.product;
import static com.hellostore.ecommerce.entity.QUser.*;
import static org.springframework.util.ObjectUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class UserDslRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public UserDslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public Page<UserDto> getUsers(UserSearchCondition userSearchCondition, Pageable pageable) {

        QueryResults<UserDto> results = queryFactory.select(
                        new QUserDto(
                                user.id, user.username, user.name,
                                user.createdDate, order.paymentPrice.sum(), user.point))
                .from(user)
                .leftJoin(order).on(order.user.id.eq(user.id))
                .where(
                        nameContains(userSearchCondition.getName()),
                        usernameContains(userSearchCondition.getUsername()),
                        user.activated.eq(userSearchCondition.isActivated()),
                        userJoinDateA(userSearchCondition.getUserJoinDateA()),
                        userJoinDateB(userSearchCondition.getUserJoinDateB()),
                        purchasePriceMin(userSearchCondition.getPurchasePriceMin()),
                        purchasePriceMax(userSearchCondition.getPurchasePriceMax())
                )
                .groupBy(user.id)
                .orderBy(user.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<UserDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression userJoinDateA(String userJoinDateA) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return StringUtils.hasText(userJoinDateA)
                ? user.createdDate.goe(LocalDateTime.parse(userJoinDateA + " 00:00:00", formatter)) : null;
    }

    private BooleanExpression userJoinDateB(String userJoinDateB) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return StringUtils.hasText(userJoinDateB)
                ? user.createdDate.loe(LocalDateTime.parse(userJoinDateB + " 23:59:59", formatter)) : null;
    }

    private BooleanExpression purchasePriceMin(Integer purchasePriceMin) {
        return !isEmpty(purchasePriceMin) ? order.paymentPrice.sum().goe(purchasePriceMin) : null;
    }

    private BooleanExpression purchasePriceMax(Integer purchasePriceMax) {
        return !isEmpty(purchasePriceMax) ? order.paymentPrice.sum().loe(purchasePriceMax) : null;
    }

    private BooleanExpression nameContains(String name) {
        return hasText(name) ? user.name.contains(name) : null;
    }

    private BooleanExpression usernameContains(String username) {
        return hasText(username) ? user.name.contains(username) : null;
    }
}
