package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.QUserDto;
import com.hellostore.ecommerce.dto.TempPasswordDto;
import com.hellostore.ecommerce.dto.UserDto;
import com.hellostore.ecommerce.dto.UserSearchCondition;
import com.hellostore.ecommerce.entity.User;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.hellostore.ecommerce.entity.QOrder.order;
import static com.hellostore.ecommerce.entity.QPointHistory.pointHistory;
import static com.hellostore.ecommerce.entity.QUser.user;
import static org.springframework.util.ObjectUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class UserDslRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;
    private final PasswordEncoder passwordEncoder;

    public UserDslRepository(EntityManager em, PasswordEncoder passwordEncoder) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
        this.passwordEncoder = passwordEncoder;
    }

    public void modifyUser(UserDto userDto) {
        queryFactory.update(user)
                .set(user.address.zoneCode, userDto.getZoneCode())
                .set(user.address.roadAddress, userDto.getRoadAddress())
                .set(user.address.address, userDto.getAddress())
                .set(user.address.detailAddress, userDto.getDetailAddress())
                .set(user.phoneNumber, userDto.getPhoneNumber())
                .set(user.email, userDto.getEmail())
                .set(user.password, passwordEncoder.encode(userDto.getPassword()))
                .set(user.lastModifiedDate, LocalDateTime.now())
                .where(user.username.eq(userDto.getUsername()))
                .execute();
    }

    public void modifyPerson(UserDto userDto) {
        queryFactory.update(user)
                .set(user.address.zoneCode, userDto.getZoneCode())
                .set(user.address.roadAddress, userDto.getRoadAddress())
                .set(user.address.address, userDto.getAddress())
                .set(user.address.detailAddress, userDto.getDetailAddress())
                .set(user.phoneNumber, userDto.getPhoneNumber())
                .set(user.email, userDto.getEmail())
                .set(user.lastModifiedDate, LocalDateTime.now())
                .where(user.username.eq(userDto.getUsername()))
                .execute();
    }

    public Optional<UserDto> findByUsername(String username) {

        return Optional.ofNullable(
                queryFactory.select(
                        new QUserDto(user.id, user.username, user.name, user.createdDate,
                                user.email, user.phoneNumber,
                                user.address.zoneCode, user.address.roadAddress,
                                user.address.address, user.address.detailAddress,
                                ExpressionUtils.as(
                                        JPAExpressions.select(
                                                order.paymentPrice.sum().coalesce(0)
                                        ).from(order)
                                                .where(order.user.id.eq(user.id)), "paymentPriceSum"),
                                ExpressionUtils.as(
                                        JPAExpressions.select(
                                                pointHistory.point.sum().coalesce(0)
                                        ).from(pointHistory)
                                                .where(pointHistory.user.id.eq(user.id)), "pointSum")
                                )
                        )
                .from(user)
                .where(user.username.eq(username))
                .fetchOne());
    }

    public Page<UserDto> getUsers(UserSearchCondition userSearchCondition, Pageable pageable) {

        List<UserDto> content = queryFactory.select(
                        new QUserDto(
                                user.id, user.username, user.name,
                                user.createdDate,
                                ExpressionUtils.as(
                                        JPAExpressions.select(
                                                        order.paymentPrice.sum().coalesce(0)
                                                ).from(order)
                                                .where(order.user.id.eq(user.id)), "paymentPriceSum"),
                                ExpressionUtils.as(
                                        JPAExpressions.select(
                                                        pointHistory.point.sum().coalesce(0)
                                                ).from(pointHistory)
                                                .where(pointHistory.user.id.eq(user.id)), "pointSum")
                        )
                )
                .from(user)
                .where(
                        nameContains(userSearchCondition.getName()),
                        usernameContains(userSearchCondition.getUsername()),
                        user.activated.eq(userSearchCondition.isActivated()),
                        userJoinDateA(userSearchCondition.getUserJoinDateA()),
                        userJoinDateB(userSearchCondition.getUserJoinDateB())
                )
                .having(
                        purchasePriceMin(userSearchCondition.getPurchasePriceMin()),
                        purchasePriceMax(userSearchCondition.getPurchasePriceMax())
                )
                .orderBy(user.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<User> countQuery = queryFactory
                .select(user)
                .from(user)
                .leftJoin(order).on(order.user.id.eq(user.id))
                .where(
                        nameContains(userSearchCondition.getName()),
                        usernameContains(userSearchCondition.getUsername()),
                        user.activated.eq(userSearchCondition.isActivated()),
                        userJoinDateA(userSearchCondition.getUserJoinDateA()),
                        userJoinDateB(userSearchCondition.getUserJoinDateB())
                )
                .groupBy(user.id)
                .having(
                        purchasePriceMin(userSearchCondition.getPurchasePriceMin()),
                        purchasePriceMax(userSearchCondition.getPurchasePriceMax())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
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
        return hasText(username) ? user.username.contains(username) : null;
    }

    public String getUsername(String name, String email) {
        return queryFactory.select(user.username)
                .from(user)
                .where(user.name.eq(name).and(user.email.eq(email)))
                .fetchOne();
    }

    public boolean userExist(TempPasswordDto tempPasswordDto) {

        Integer fetchFirst = queryFactory.selectOne()
                .from(user)
                .where(
                        user.username.eq(tempPasswordDto.getUsername()),
                        user.name.eq(tempPasswordDto.getPersonName()),
                        user.email.eq(tempPasswordDto.getEmailAddress())
                ).fetchFirst();

        return fetchFirst != null;
    }

    public void modifyPassword(UserDto userDto) {
        queryFactory.update(user)
                .set(user.password, passwordEncoder.encode(userDto.getNewPassword()))
                .where(user.username.eq(userDto.getUsername()))
                .execute();
    }
}
