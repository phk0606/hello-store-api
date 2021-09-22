package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.OrderProductDto;
import com.hellostore.ecommerce.dto.QOrderProductDto;
import com.hellostore.ecommerce.entity.OrderProduct;
import com.hellostore.ecommerce.entity.QOrder;
import com.hellostore.ecommerce.entity.QProductComment;
import com.hellostore.ecommerce.enumType.ImageType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.hellostore.ecommerce.entity.QOrder.*;
import static com.hellostore.ecommerce.entity.QOrderProduct.orderProduct;
import static com.hellostore.ecommerce.entity.QProduct.product;
import static com.hellostore.ecommerce.entity.QProductComment.*;
import static com.hellostore.ecommerce.entity.QProductImage.productImage;

@Repository
public class OrderProductRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public OrderProductRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public OrderProduct getOrderProductById(Long orderProductId) {
        return queryFactory.selectFrom(orderProduct)
                .where(orderProduct.id.eq(orderProductId))
                .fetchOne();
    }

    public List<OrderProductDto> getOrderProducts(Long orderId) {

        // orderProduct 조회
        return queryFactory.select(
                        new QOrderProductDto(
                                orderProduct.product.id,
                                product.name,
                                orderProduct.id, orderProduct.salePrice,
                                orderProduct.quantity, orderProduct.point,
                                orderProduct.shippingFee, orderProduct.totalPrice,
                                productImage.filePath, productImage.fileName
                        ))
                .from(orderProduct)
                .join(product).on(product.id.eq(orderProduct.product.id))
                .leftJoin(productImage).on(productImage.product.id.eq(orderProduct.product.id))
                .on(productImage.imageType.eq(ImageType.LIST))
                .where(orderProduct.order.id.eq(orderId))
                .fetch();
    }

    public List<OrderProductDto> getOrderProductsByUsername(String username) {

        return queryFactory.select(
                new QOrderProductDto(order.id, order.createdDate, orderProduct.id, product.name))
                .from(order)
                .join(orderProduct).on(orderProduct.order.id.eq(order.id))
                .join(product).on(product.id.eq(orderProduct.product.id))
                .leftJoin(productComment)
                .on(productComment.createdBy.eq(order.user.username))
                .on((productComment.orderProduct.id.eq(orderProduct.id)))
                .where(
                        order.user.username.eq(username),
                        order.createdDate.goe(LocalDateTime.now().minusMonths(1)),
                        productComment.content.isNull()
                )
                .orderBy(order.createdDate.desc(), order.id.desc())
                .fetch();
    }
}
