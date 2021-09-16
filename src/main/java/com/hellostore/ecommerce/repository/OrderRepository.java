package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.*;
import com.hellostore.ecommerce.entity.*;
import com.hellostore.ecommerce.enumType.ImageType;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QDelivery.delivery;
import static com.hellostore.ecommerce.entity.QOrder.order;
import static com.hellostore.ecommerce.entity.QProduct.product;
import static com.hellostore.ecommerce.entity.QUser.user;

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

    public void modifyOrdererPhoneNumber(OrderDto orderDto) {

        queryFactory.update(order)
                .set(order.phoneNumber, orderDto.getPhoneNumber())
                .where(order.id.eq(orderDto.getOrderId()))
                .execute();
    }

    public void modifyDeliveryInfo(OrderDto orderDto) {

        QDelivery delivery = QDelivery.delivery;
        queryFactory.update(delivery)
                .set(delivery.address.zoneCode, orderDto.getAddress().getZoneCode())
                .set(delivery.address.roadAddress, orderDto.getAddress().getRoadAddress())
                .set(delivery.address.address, orderDto.getAddress().getAddress())
                .set(delivery.address.detailAddress, orderDto.getAddress().getDetailAddress())
                .set(delivery.recipientName, orderDto.getRecipientName())
                .set(delivery.requirement, orderDto.getRequirement())
                .set(delivery.phoneNumber, orderDto.getRecipientPhoneNumber())
                .execute();
    }

    public OrderDto getOrder(Long orderId) {

        return queryFactory.select(
                new QOrderDto(
                        order.id, order.createdDate, order.user.id, user.username, user.name,
                        order.phoneNumber, order.paymentMethodType, order.paymentPrice,
                        order.depositAccount, order.depositorName, order.depositDueDate,
                        order.status,
                        delivery.recipientName, delivery.phoneNumber, delivery.requirement,
                        delivery.address
                ))
                .from(order)
                .join(user).on(order.user.id.eq(user.id))
                .join(delivery).on(order.delivery.id.eq(delivery.id))
                .where(order.id.eq(orderId))
                .fetchOne();
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public Page<OrderDto> getOrdersByUsername(Pageable pageable, String username) {

        QOrderProduct orderProduct = QOrderProduct.orderProduct;

        QueryResults<OrderDto> results = queryFactory.select(
                        new QOrderDto(order.id, order.createdDate, order.user.id, user.username, user.name,
                                order.phoneNumber, order.paymentMethodType, order.paymentPrice,
                                order.depositAccount, order.depositorName, order.depositDueDate,
                                order.paymentStatus, order.status,
                                delivery.recipientName, delivery.phoneNumber, delivery.requirement,
                                delivery.address, orderProduct.id.count()))
                .from(order)
                .join(user).on(order.user.id.eq(user.id))
                .join(delivery).on(order.delivery.id.eq(delivery.id))
                .join(orderProduct).on(orderProduct.order.id.eq(order.id))
                .where(user.username.eq(username))
                .groupBy(order.id)
                .orderBy(order.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<OrderDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    public List<OrderProductDto> getOrderProduct(List<Long> orderIds) {
        QProductImage productImage = QProductImage.productImage;
        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        QProduct product = QProduct.product;

        List<OrderProductDto> orderProductDtos = queryFactory.select(
                        new QOrderProductDto(
                                orderProduct.order.id,
                                orderProduct.id, product.name,
                                productImage.filePath, productImage.fileName
                                ))
                .from(orderProduct)
                .join(product).on(orderProduct.product.id.eq(product.id))
                .join(productImage).on(product.id.eq(productImage.product.id))
                .on(productImage.imageType.eq(ImageType.LIST))
                .where(orderProduct.order.id.in(orderIds))
                .groupBy(orderProduct.order.id)
                .fetch();

        return orderProductDtos;
    }

    public Page<OrderDto> getOrders(Pageable pageable) {

        QOrderProduct orderProduct = QOrderProduct.orderProduct;

        QueryResults<OrderDto> results = queryFactory.select(
                        new QOrderDto(order.id, order.createdDate, order.user.id, user.username, user.name,
                                order.phoneNumber, order.paymentMethodType, order.paymentPrice,
                                order.depositAccount, order.depositorName, order.depositDueDate,
                                order.paymentStatus, order.status,
                                delivery.recipientName, delivery.phoneNumber, delivery.requirement,
                                delivery.address, orderProduct.id.count()))
                .from(order)
                .join(user).on(order.user.id.eq(user.id))
                .join(delivery).on(order.delivery.id.eq(delivery.id))
                .join(orderProduct).on(orderProduct.order.id.eq(order.id))
                .groupBy(order.id)
                .orderBy(order.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<OrderDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
