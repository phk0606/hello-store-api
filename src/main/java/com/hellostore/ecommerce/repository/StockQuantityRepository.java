package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.QStockQuantityDto;
import com.hellostore.ecommerce.dto.StockQuantityDto;
import com.hellostore.ecommerce.entity.QProduct;
import com.hellostore.ecommerce.entity.QProductOption;
import com.hellostore.ecommerce.entity.QStockQuantity;
import com.hellostore.ecommerce.entity.StockQuantity;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class StockQuantityRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public StockQuantityRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public StockQuantity createStockQuantity(StockQuantity stockQuantity) {
        em.persist(stockQuantity);
        return stockQuantity;
    }

    public Page<StockQuantityDto> getStockQuantities(Pageable pageable) {

        QStockQuantity stockQuantity = QStockQuantity.stockQuantity1;
        QProduct product = QProduct.product;
        QProductOption firstOption = new QProductOption("firstOption");
        QProductOption secondOption = new QProductOption("secondOption");

        QueryResults<StockQuantityDto> results = queryFactory.select(
                        new QStockQuantityDto(
                                stockQuantity.id, stockQuantity.product.id, product.name,
                                stockQuantity.firstOption.id,
                                firstOption.optionName, firstOption.optionValue,
                                stockQuantity.secondOption.id,
                                secondOption.optionName, secondOption.optionValue,
                                stockQuantity.stockQuantity
                        )
                )
                .from(stockQuantity)
                .join(product).on(product.id.eq(stockQuantity.product.id))
                .join(firstOption).on(firstOption.id.eq(stockQuantity.firstOption.id))
                .join(secondOption).on(secondOption.id.eq(stockQuantity.secondOption.id))
                .orderBy(
                        stockQuantity.product.id.asc(),
                        stockQuantity.firstOption.id.asc(),
                        stockQuantity.secondOption.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<StockQuantityDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
