package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.QUserDto;
import com.hellostore.ecommerce.dto.UserDto;
import com.hellostore.ecommerce.entity.QOrder;
import com.hellostore.ecommerce.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserDslRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public UserDslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public List<UserDto> getUsers() {
        QUser user = QUser.user;
        QOrder order = QOrder.order;

        return queryFactory.select(
                new QUserDto(
                        user.id, user.username, user.name,
                        user.createdDate, order.paymentPrice.sum(), user.point))
                .from(user)
                .leftJoin(order).on(order.user.id.eq(user.id))
                .groupBy(user.id)
                .orderBy(user.createdDate.asc())
                .fetch();
    }
}
