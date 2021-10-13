package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ExchangeRefundProductDto;
import com.hellostore.ecommerce.dto.QExchangeRefundProductDto;
import com.hellostore.ecommerce.entity.ExchangeRefundProduct;
import com.hellostore.ecommerce.entity.QExchangeRefundProduct;
import com.hellostore.ecommerce.entity.QOrderProduct;
import com.hellostore.ecommerce.enumType.ImageType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QProduct.product;
import static com.hellostore.ecommerce.entity.QProductImage.productImage;

@Repository
public class ExchangeRefundProductRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ExchangeRefundProductRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public void createExchangeRefundProduct(ExchangeRefundProduct exchangeRefundProduct) {
        em.persist(exchangeRefundProduct);
    }

    public List<ExchangeRefundProductDto> getExchangeRefundProduct(List<Long> exchangeRefundIds) {

        QExchangeRefundProduct exchangeRefundProduct = QExchangeRefundProduct.exchangeRefundProduct;
        QOrderProduct orderProduct = QOrderProduct.orderProduct;

        List<ExchangeRefundProductDto> exchangeRefundProductDtos = queryFactory.select(
                        new QExchangeRefundProductDto(
                                exchangeRefundProduct.orderProduct.id,
                                exchangeRefundProduct.exchangeRefundType,
                                exchangeRefundProduct.exchangeRefund.id,
                                exchangeRefundProduct.id,
                                product.name,
                                orderProduct.salePrice,
                                orderProduct.quantity,
                                productImage.imageFile.filePath, productImage.imageFile.fileName
                        )
                )
                .from(exchangeRefundProduct)
                .join(orderProduct).on(orderProduct.id.eq(exchangeRefundProduct.orderProduct.id))
                .join(product).on(exchangeRefundProduct.orderProduct.product.id.eq(product.id))
                .leftJoin(productImage)
                .on(productImage.product.id.eq(orderProduct.product.id))
                .on(productImage.imageType.eq(ImageType.LIST))
                .where(exchangeRefundProduct.exchangeRefund.id.in(exchangeRefundIds))
                .fetch();

        return exchangeRefundProductDtos;
    }
}
