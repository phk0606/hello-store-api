package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.OrderDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.hellostore.ecommerce.entity.QDelivery.delivery;

@Repository
public class DeliveryRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public DeliveryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public void modifyDelivery(OrderDto orderDto) {

        queryFactory.update(delivery)
                .set(delivery.recipientName, orderDto.getRecipientName())
                .set(delivery.address.zoneCode, orderDto.getAddress().getZoneCode())
                .set(delivery.address.roadAddress, orderDto.getAddress().getRoadAddress())
                .set(delivery.address.address, orderDto.getAddress().getAddress())
                .set(delivery.address.detailAddress, orderDto.getAddress().getDetailAddress())
                .set(delivery.phoneNumber, orderDto.getRecipientPhoneNumber())
                .set(delivery.requirement, orderDto.getRequirement())
                .where(delivery.id.eq(orderDto.getDeliveryId()))
                .execute();
    }
}
