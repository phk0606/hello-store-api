package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.*;
import com.hellostore.ecommerce.entity.*;
import com.hellostore.ecommerce.enumType.ImageType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.hellostore.ecommerce.entity.QDelivery.delivery;
import static com.hellostore.ecommerce.entity.QOrder.order;
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

    public OrderDto getOrder(Long orderId) {

        return queryFactory.select(
                new QOrderDto(
                        order.id, order.user.id, user.username, user.name,
                        order.phoneNumber, order.paymentMethodType, order.paymentPrice,
                        order.depositAccount, order.depositorName, order.depositDueDate,
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

    public List<OrderDto> getOrdersByUsername(String username) {

        return queryFactory.select(
                new QOrderDto(order.id, order.createdDate, order.user.id, user.username, user.name,
                        order.phoneNumber, order.paymentMethodType, order.paymentPrice,
                        order.depositAccount, order.depositorName, order.depositDueDate,
                        order.paymentStatus, order.status,
                        delivery.recipientName, delivery.phoneNumber, delivery.requirement,
                        delivery.address, delivery.status))
                .from(order)
                .join(user).on(order.user.id.eq(user.id))
                .join(delivery).on(order.delivery.id.eq(delivery.id))
                .where(user.username.eq(username))
                .fetch();
    }

    public ProductImage getOrderProductListImage(Long orderId) {

        QProductImage productImage = QProductImage.productImage;
        QOrderProduct orderProduct = QOrderProduct.orderProduct;

        return queryFactory.select(productImage)
                .from(orderProduct)
                .where(orderProduct.order.id.eq(orderId))
                .orderBy(orderProduct.id.asc())
                .limit(1)
                .join(productImage).on(productImage.product.id.eq(orderProduct.product.id))
                .on(productImage.imageType.eq(ImageType.LIST))
                .fetchOne();
    }

//    public List<com.querydsl.core.Tuple> getOrderProduct(List<Long> orderIds) {
//
//        QProductImage productImage = QProductImage.productImage;
//        QOrderProduct orderProduct = QOrderProduct.orderProduct;
//        QProduct product = QProduct.product;
//
//        List<com.querydsl.core.Tuple> fetch =
//                queryFactory.select(
//                        orderProduct.order.id, orderProduct.id.min()
//                )
//                .from(orderProduct)
//                .where(orderProduct.order.id.in(orderIds))
//                .groupBy(orderProduct.order.id)
//                .fetch();
//        return fetch;
//    }

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
                .fetch();

        return orderProductDtos;
    }
}
