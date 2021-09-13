package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.OrderProductDto;
import com.hellostore.ecommerce.dto.QOrderProductDto;
import com.hellostore.ecommerce.entity.QOrderProduct;
import com.hellostore.ecommerce.entity.QProduct;
import com.hellostore.ecommerce.entity.QProductImage;
import com.hellostore.ecommerce.enumType.ImageType;
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
        QProductImage productImage = QProductImage.productImage;
        QProduct product = QProduct.product;

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
}
