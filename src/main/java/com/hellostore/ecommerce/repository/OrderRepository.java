package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.*;
import com.hellostore.ecommerce.entity.Order;
import com.hellostore.ecommerce.entity.QBankAccount;
import com.hellostore.ecommerce.enumType.ImageType;
import com.hellostore.ecommerce.enumType.OrderDeliveryStatus;
import com.hellostore.ecommerce.enumType.PaymentStatus;
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

import static com.hellostore.ecommerce.entity.QBankAccount.*;
import static com.hellostore.ecommerce.entity.QDelivery.delivery;
import static com.hellostore.ecommerce.entity.QOrder.order;
import static com.hellostore.ecommerce.entity.QOrderProduct.orderProduct;
import static com.hellostore.ecommerce.entity.QProduct.product;
import static com.hellostore.ecommerce.entity.QProductImage.productImage;
import static com.hellostore.ecommerce.entity.QUser.user;
import static org.springframework.util.ObjectUtils.isEmpty;

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
                        order.id, order.createdDate, order.orderCancelDate,
                        order.user.id, user.username, user.name,
                        order.phoneNumber, order.paymentMethodType, order.paymentPrice,
                        order.paymentStatus,
                        bankAccount.bankName.concat(" ").concat(bankAccount.accountNumber).concat(" ")
                                .concat(bankAccount.accountHolder).as("depositAccount"),
                        order.depositorName, order.depositDueDate,
                        order.status,
                        delivery.recipientName, delivery.phoneNumber, delivery.requirement,
                        delivery.address
                ))
                .from(order)
                .join(user).on(order.user.id.eq(user.id))
                .join(delivery).on(order.delivery.id.eq(delivery.id))
                .leftJoin(bankAccount).on(bankAccount.id.eq(order.bankAccount.id))
                .where(order.id.eq(orderId))
                .fetchOne();
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public Page<OrderDto> getOrdersByUsername(Pageable pageable, OrderSearchCondition orderSearchCondition) {

        QueryResults<OrderDto> results = queryFactory.select(
                        new QOrderDto(order.id, order.createdDate, order.orderCancelDate,
                                order.user.id, user.username, user.name,
                                order.phoneNumber, order.paymentMethodType, order.paymentPrice,
                                bankAccount.bankName.concat(" ").concat(bankAccount.accountNumber).concat(" ")
                                        .concat(bankAccount.accountHolder).as("depositAccount"),
                                order.depositorName, order.depositDueDate,
                                order.paymentStatus, order.status,
                                delivery.recipientName, delivery.phoneNumber, delivery.requirement,
                                delivery.address, orderProduct.id.count()))
                .from(order)
                .join(user).on(order.user.id.eq(user.id))
                .join(delivery).on(order.delivery.id.eq(delivery.id))
                .join(orderProduct).on(orderProduct.order.id.eq(order.id))
                .leftJoin(bankAccount).on(bankAccount.id.eq(order.bankAccount.id))
                .where(
                        user.username.eq(orderSearchCondition.getUsername()),
                        orderDateA(orderSearchCondition.getOrderDateA()),
                        orderDateB(orderSearchCondition.getOrderDateB())
                        )
                .groupBy(order.id)
                .orderBy(order.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<OrderDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression orderDateA(String orderDateA) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return StringUtils.hasText(orderDateA)
                ? order.createdDate.goe(LocalDateTime.parse(orderDateA + " 00:00:00", formatter)) : null;
    }

    private BooleanExpression orderDateB(String orderDateB) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return StringUtils.hasText(orderDateB)
                ? order.createdDate.loe(LocalDateTime.parse(orderDateB + " 23:59:59", formatter)) : null;
    }

    public List<OrderProductDto> getOrderProduct(List<Long> orderIds) {

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

    public Page<OrderDto> getOrders(Pageable pageable, OrderSearchCondition orderSearchCondition) {

        QueryResults<OrderDto> results = queryFactory.select(
                        new QOrderDto(order.id, order.createdDate, order.orderCancelDate,
                                order.user.id, user.username, user.name,
                                order.phoneNumber, order.paymentMethodType, order.paymentPrice,
                                bankAccount.bankName.concat(" ").concat(bankAccount.accountNumber).concat(" ")
                                        .concat(bankAccount.accountHolder).as("depositAccount"),
                                order.depositorName, order.depositDueDate,
                                order.paymentStatus, order.status,
                                delivery.recipientName, delivery.phoneNumber, delivery.requirement,
                                delivery.address, orderProduct.id.count()))
                .from(order)
                .where(
                        orderDeliveryStatusEq(orderSearchCondition.getOrderDeliveryStatus(),
                                orderSearchCondition.getPaymentStatus()),
                        orderIdEq(orderSearchCondition.getOrderId()),
                        ordererIdEq(orderSearchCondition.getOrdererId()),
                        ordererNameContains(orderSearchCondition.getOrdererName()),
                        productNameContains(orderSearchCondition.getProductName())
                )
                .join(user).on(order.user.id.eq(user.id))
                .join(delivery).on(order.delivery.id.eq(delivery.id))
                .join(orderProduct).on(orderProduct.order.id.eq(order.id))
                .leftJoin(bankAccount).on(bankAccount.id.eq(order.bankAccount.id))
                .groupBy(order.id)
                .orderBy(order.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<OrderDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression orderDeliveryStatusEq(
            OrderDeliveryStatus orderDeliveryStatus, PaymentStatus paymentStatus) {

        return !isEmpty(orderDeliveryStatus)
                ? order.status.eq(orderDeliveryStatus)
                .and(order.status.ne(OrderDeliveryStatus.ORDER_CANCEL))
                : order.status.eq(OrderDeliveryStatus.ORDER_CANCEL)
                .and(order.paymentStatus.eq(paymentStatus));
    }

    private BooleanExpression orderIdEq(Long orderId) {
        return !isEmpty(orderId)
                ? order.id.eq(orderId) : null;
    }

    private BooleanExpression ordererIdEq(String ordererId) {
        return !isEmpty(ordererId)
                ? order.user.username.eq(ordererId) : null;
    }

    private BooleanExpression ordererNameContains(String ordererName) {
        return !isEmpty(ordererName)
                ? order.user.name.contains(ordererName) : null;
    }

    private BooleanExpression productNameContains(String productName) {
        return !isEmpty(productName)
                ? orderProduct.product.name.contains(productName) : null;
    }

    public void modifyOrder(OrderDto orderDto) {

        queryFactory.update(order)
                .set(order.paymentStatus, orderDto.getPaymentStatus())
                .set(order.status, orderDto.getOrderDeliveryStatus())
                .set(order.delivery.recipientName, orderDto.getRecipientName())
                .set(order.delivery.address.zoneCode, orderDto.getAddress().getZoneCode())
                .set(order.delivery.address.roadAddress, orderDto.getAddress().getRoadAddress())
                .set(order.delivery.address.address, orderDto.getAddress().getAddress())
                .set(order.delivery.address.detailAddress, orderDto.getAddress().getDetailAddress())
                .set(order.delivery.phoneNumber, orderDto.getRecipientPhoneNumber())
                .set(order.delivery.requirement, orderDto.getRequirement())
                .execute();
    }

    public void modifyOrderDeliveryStatus(List<Long> orderIds, OrderDeliveryStatus orderDeliveryStatus) {
        queryFactory.update(order)
                .set(order.status, orderDeliveryStatus)
                .where(order.id.in(orderIds))
                .execute();
    }

    public void modifyPaymentStatus(List<Long> orderIds, PaymentStatus paymentStatus) {
        queryFactory.update(order)
                .set(order.paymentStatus, paymentStatus)
                .where(order.id.in(orderIds))
                .execute();
    }
}
