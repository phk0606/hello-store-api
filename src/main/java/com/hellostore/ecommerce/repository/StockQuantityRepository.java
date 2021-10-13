package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.*;
import com.hellostore.ecommerce.entity.QProductOption;
import com.hellostore.ecommerce.entity.StockQuantity;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QProduct.product;
import static com.hellostore.ecommerce.entity.QProductOption.productOption;
import static com.hellostore.ecommerce.entity.QStockQuantity.stockQuantity1;
import static org.springframework.util.ObjectUtils.isEmpty;

@Repository
public class StockQuantityRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public StockQuantityRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public void removeStockQuantity(Long productId) {
        queryFactory.delete(stockQuantity1)
                .where(stockQuantity1.product.id.eq(productId))
                .execute();
    }

    public List<ProductOptionDto> getFirstOptionsInStockQuantity(Long productId) {

        return queryFactory.select(
                new QProductOptionDto(
                        stockQuantity1.firstOption.id,
                        productOption.optionGroupNumber,
                        productOption.optionName,
                        productOption.optionValue))
                .from(stockQuantity1)
                .where(stockQuantity1.product.id.eq(productId))
                .join(productOption).on(productOption.id.eq(stockQuantity1.firstOption.id))
                .fetch();
    }

    public List<ProductOptionDto> getSecondOptionsInStockQuantity(Long productId, Long firstOptionId) {

        return queryFactory.select(
                        new QProductOptionDto(
                                stockQuantity1.secondOption.id,
                                productOption.optionGroupNumber,
                                productOption.optionName,
                                productOption.optionValue,
                                stockQuantity1.stockQuantity))
                .from(stockQuantity1)
                .where(
                        stockQuantity1.product.id.eq(productId),
                        stockQuantity1.firstOption.id.eq(firstOptionId)
                        )
                .join(productOption).on(productOption.id.eq(stockQuantity1.secondOption.id))
                .fetch();
    }

    public StockQuantity createStockQuantity(StockQuantity stockQuantity) {
        em.persist(stockQuantity);
        return stockQuantity;
    }

    public void modifyStockQuantity(StockQuantityDto stockQuantityDto) {

        queryFactory.update(stockQuantity1)
                .set(stockQuantity1.stockQuantity, stockQuantityDto.getStockQuantity())
                .where(stockQuantity1.id.eq(stockQuantityDto.getStockQuantityId()))
                .execute();
    }

    public boolean stockQuantityCheck(StockQuantityDto stockQuantityDto) {

        Long fetchOne = queryFactory.select(stockQuantity1.id.count().subtract(stockQuantityDto.getStockQuantity()))
                .from(stockQuantity1)
                .where(
                        stockQuantity1.product.id.eq(stockQuantityDto.getProductId()),
                        stockQuantity1.firstOption.id.eq(stockQuantityDto.getFirstOptionId()),
                        stockQuantity1.secondOption.id.eq(stockQuantityDto.getSecondOptionId())
                ).fetchOne();
        fetchOne < 0
    }

    public void subtractStockQuantity(StockQuantityDto stockQuantityDto) {

        queryFactory.update(stockQuantity1)
                .set(stockQuantity1.stockQuantity,
                        stockQuantity1.stockQuantity.subtract(stockQuantityDto.getStockQuantity()))
                .where(
                        stockQuantity1.product.id.eq(stockQuantityDto.getProductId()),
                        stockQuantity1.firstOption.id.eq(stockQuantityDto.getFirstOptionId()),
                        stockQuantity1.secondOption.id.eq(stockQuantityDto.getSecondOptionId())
                        )
                .execute();
    }

    public void addStockQuantity(StockQuantityDto stockQuantityDto) {

        queryFactory.update(stockQuantity1)
                .set(stockQuantity1.stockQuantity,
                        stockQuantity1.stockQuantity.add(stockQuantityDto.getStockQuantity()))
                .where(
                        stockQuantity1.product.id.eq(stockQuantityDto.getProductId()),
                        stockQuantity1.firstOption.id.eq(stockQuantityDto.getFirstOptionId()),
                        stockQuantity1.secondOption.id.eq(stockQuantityDto.getSecondOptionId())
                )
                .execute();
    }

    public Page<StockQuantityDto> getStockQuantities(
            StockQuantitySearchCondition stockQuantitySearchCondition, Pageable pageable) {

        QProductOption firstOption = new QProductOption("firstOption");
        QProductOption secondOption = new QProductOption("secondOption");

        QueryResults<StockQuantityDto> results = queryFactory.select(
                        new QStockQuantityDto(
                                stockQuantity1.id, stockQuantity1.product.id, product.name,
                                stockQuantity1.firstOption.id,
                                firstOption.optionName, firstOption.optionValue,
                                stockQuantity1.secondOption.id,
                                secondOption.optionName, secondOption.optionValue,
                                stockQuantity1.stockQuantity
                        )
                )
                .from(stockQuantity1)
                .where(
                        productIdEq(stockQuantitySearchCondition.getProductId()),
                        firstOptionIdEq(stockQuantitySearchCondition.getFirstOptionId()),
                    secondOptionIdEq(stockQuantitySearchCondition.getSecondOptionId()),
                    productNameContains(stockQuantitySearchCondition.getSearchText()),
                        stockQuantityMin(stockQuantitySearchCondition.getStockQuantityMin()),
                        stockQuantityMax(stockQuantitySearchCondition.getStockQuantityMax())
                )
                .join(product).on(product.id.eq(stockQuantity1.product.id))
                .join(firstOption).on(firstOption.id.eq(stockQuantity1.firstOption.id))
                .join(secondOption).on(secondOption.id.eq(stockQuantity1.secondOption.id))
                .orderBy(
                        stockQuantity1.product.id.asc(),
                        stockQuantity1.firstOption.id.asc(),
                        stockQuantity1.secondOption.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<StockQuantityDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression productIdEq(Long productId) {
        return !isEmpty(productId)
                ? stockQuantity1.product.id.eq(productId) : null;
    }

    private BooleanExpression firstOptionIdEq(Long firstOptionId) {
        return !isEmpty(firstOptionId)
                ? stockQuantity1.firstOption.id.eq(firstOptionId) : null;
    }

    private BooleanExpression secondOptionIdEq(Long secondOptionId) {
        return !isEmpty(secondOptionId)
                ? stockQuantity1.secondOption.id.eq(secondOptionId) : null;
    }

    private BooleanExpression productNameContains(String searchText) {
        return !isEmpty(searchText)
                ? product.name.contains(searchText) : null;
    }

    private BooleanExpression stockQuantityMin(Integer stockQuantityMin) {
        return !isEmpty(stockQuantityMin) ? stockQuantity1.stockQuantity.goe(stockQuantityMin) : null;
    }

    private BooleanExpression stockQuantityMax(Integer stockQuantityMax) {
        return !isEmpty(stockQuantityMax) ? stockQuantity1.stockQuantity.loe(stockQuantityMax) : null;
    }
}
