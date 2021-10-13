package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ExchangeRefundImageDto;
import com.hellostore.ecommerce.dto.QExchangeRefundImageDto;
import com.hellostore.ecommerce.entity.ExchangeRefundImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QExchangeRefundImage.exchangeRefundImage;

@Repository
public class ExchangeRefundImageRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ExchangeRefundImageRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public void createExchangeRefundImage(ExchangeRefundImage exchangeRefundImage) {

        em.persist(exchangeRefundImage);
    }

    public List<ExchangeRefundImageDto> getExchangeRefundImages(Long exchangeRefundId) {

        return queryFactory.select(
                new QExchangeRefundImageDto(
                        exchangeRefundImage.imageFile.originalFileName,
                        exchangeRefundImage.imageFile.fileName,
                        exchangeRefundImage.imageFile.filePath,
                        exchangeRefundImage.imageFile.fileSize))
                .from(exchangeRefundImage)
                .where(exchangeRefundImage.exchangeRefund.id.eq(exchangeRefundId))
                .fetch();
    }
}
