package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ExchangeReturnProductDto;
import com.hellostore.ecommerce.dto.QExchangeReturnProductDto;
import com.hellostore.ecommerce.entity.ExchangeReturnProduct;
import com.hellostore.ecommerce.entity.QExchangeReturnProduct;
import com.hellostore.ecommerce.entity.QOrderProduct;
import com.hellostore.ecommerce.enumType.ImageType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

        QExchangeReturnProduct exchangeReturnProduct = QExchangeReturnProduct.exchangeReturnProduct;
        QOrderProduct orderProduct = QOrderProduct.orderProduct;

        List<ExchangeReturnProductDto> exchangeReturnProductDtos = queryFactory.select(
                        new QExchangeReturnProductDto(
                                exchangeReturnProduct.orderProduct.id,
                                exchangeReturnProduct.exchangeReturnType,
                                exchangeReturnProduct.exchangeReturn.id,
                                exchangeReturnProduct.id,
                                product.name,
                                orderProduct.salePrice,
                                orderProduct.quantity,
                                productImage.imageFile.filePath, productImage.imageFile.fileName
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
}
