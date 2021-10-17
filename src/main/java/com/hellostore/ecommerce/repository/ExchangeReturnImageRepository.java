package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ExchangeReturnImageDto;
import com.hellostore.ecommerce.dto.QExchangeReturnImageDto;
import com.hellostore.ecommerce.entity.ExchangeReturnImage;
import com.hellostore.ecommerce.entity.QExchangeReturnImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ExchangeReturnImageRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ExchangeReturnImageRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public void createExchangeReturnImage(ExchangeReturnImage exchangeReturnImage) {

        em.persist(exchangeReturnImage);
    }

    public List<ExchangeReturnImageDto> getExchangeReturnImages(Long exchangeReturnId) {

        QExchangeReturnImage exchangeReturnImage = QExchangeReturnImage.exchangeReturnImage;
        return queryFactory.select(
                new QExchangeReturnImageDto(
                        exchangeReturnImage.imageFile.originalFileName,
                        exchangeReturnImage.imageFile.fileName,
                        exchangeReturnImage.imageFile.filePath,
                        exchangeReturnImage.imageFile.fileSize))
                .from(exchangeReturnImage)
                .where(exchangeReturnImage.exchangeReturn.id.eq(exchangeReturnId))
                .fetch();
    }
}
