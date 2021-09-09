package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.OrderDto;
import com.hellostore.ecommerce.dto.QOrderDto;
import com.hellostore.ecommerce.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class OrderRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public OrderRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public OrderDto getOrder(Long orderId) {
        QOrder order = QOrder.order;
        QUser user = QUser.user;
        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        QDelivery delivery = QDelivery.delivery;

        return queryFactory.select(
                new QOrderDto(
                        order.id,
                        user.username,
                        order.phoneNumber, order.paymentMethodType,
                        orderProduct.id, orderProduct.orderQuantity,
                        orderProduct.orderShippingFee, orderProduct.point,
                        orderProduct.salePrice, orderProduct.totalPrice,
                        delivery.id, delivery.address.zoneCode,
                        delivery.address.address, delivery.address.detailAddress,
                        delivery.recipientName, delivery.phoneNumber,
                        delivery.requirement)
                ).from(order)
                .join(user).on(order.user.id.eq(user.id))
                .join(orderProduct).on(orderProduct.order.id.eq(order.id))
                .join(delivery).on(order.delivery.id.eq(delivery.id))
                .where(order.id.eq(orderId))
                .fetchOne();
    }

}
