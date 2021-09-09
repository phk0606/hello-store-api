package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.OrderProductDto;
import com.hellostore.ecommerce.dto.QOrderProductDto;
import com.hellostore.ecommerce.entity.QOrderProduct;
import com.hellostore.ecommerce.entity.QOrderProductOption;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class OrderProductRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public OrderProductRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public List<OrderProductDto> getOrderProducts(Long orderId) {
        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        QOrderProductOption orderProductOption = QOrderProductOption.orderProductOption;

        // orderProduct 조회
        return queryFactory.select(
                        new QOrderProductDto(
                                orderProduct.id, orderProduct.salePrice,
                                orderProduct.orderQuantity, orderProduct.point,
                                orderProduct.orderShippingFee, orderProduct.totalPrice))
                .from(orderProduct)
                .where(orderProduct.order.id.eq(orderId))
                .fetch();
    }
}
