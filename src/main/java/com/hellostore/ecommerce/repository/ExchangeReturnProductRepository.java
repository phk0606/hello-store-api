package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ExchangeReturnDto;
import com.hellostore.ecommerce.dto.ExchangeReturnProductDto;
import com.hellostore.ecommerce.dto.QExchangeReturnProductDto;
import com.hellostore.ecommerce.entity.ExchangeReturnProduct;
import com.hellostore.ecommerce.enumType.ImageType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QExchangeReturnProduct.exchangeReturnProduct;
import static com.hellostore.ecommerce.entity.QOrderProduct.orderProduct;
import static com.hellostore.ecommerce.entity.QProduct.product;
import static com.hellostore.ecommerce.entity.QProductImage.productImage;

@Repository
public class ExchangeReturnProductRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ExchangeReturnProductRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public void createExchangeReturnProduct(ExchangeReturnProduct exchangeReturnProduct) {
        em.persist(exchangeReturnProduct);
    }

    public List<ExchangeReturnProductDto> getExchangeReturnProduct(List<Long> exchangeReturnIds) {

        List<ExchangeReturnProductDto> exchangeReturnProductDtos = queryFactory.select(
                        new QExchangeReturnProductDto(
                                exchangeReturnProduct.orderProduct.id,
                                exchangeReturnProduct.exchangeReturnType,
                                exchangeReturnProduct.exchangeReturn.id,
                                exchangeReturnProduct.id,
                                product.id,
                                product.name,
                                orderProduct.salePrice,
                                orderProduct.quantity,
                                orderProduct.point,
                                productImage.imageFile.filePath, productImage.imageFile.fileName,
                                exchangeReturnProduct.newOrderId
                        )
                )
                .from(exchangeReturnProduct)
                .join(orderProduct).on(orderProduct.id.eq(exchangeReturnProduct.orderProduct.id))
                .join(product).on(exchangeReturnProduct.orderProduct.product.id.eq(product.id))
                .leftJoin(productImage)
                .on(productImage.product.id.eq(orderProduct.product.id))
                .on(productImage.imageType.eq(ImageType.LIST))
                .where(exchangeReturnProduct.exchangeReturn.id.in(exchangeReturnIds))
                .fetch();

        return exchangeReturnProductDtos;
    }

    public void removeExchangeReturnProduct(ExchangeReturnDto exchangeReturnDto) {
        queryFactory.delete(exchangeReturnProduct)
                .where(exchangeReturnProduct.exchangeReturn.id.eq(exchangeReturnDto.getExchangeReturnId()))
                .execute();
    }

    public void updateNewOrderId(Long exchangeReturnProductId, Long newOrderId) {
        queryFactory.update(exchangeReturnProduct)
                .set(exchangeReturnProduct.newOrderId, newOrderId)
                .where(exchangeReturnProduct.id.eq(exchangeReturnProductId))
                .execute();
    }
}
