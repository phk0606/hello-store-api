package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.*;
import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.entity.QProductOption;
import com.hellostore.ecommerce.entity.QStockQuantity;
import com.hellostore.ecommerce.enumType.ImageType;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.hellostore.ecommerce.entity.QCategory.category;
import static com.hellostore.ecommerce.entity.QCategoryProduct.categoryProduct;
import static com.hellostore.ecommerce.entity.QProduct.product;
import static com.hellostore.ecommerce.entity.QProductImage.productImage;
import static org.springframework.util.ObjectUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;

@Repository
@Slf4j
public class ShopProductRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ShopProductRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public void modifyClickCount(Long productId) {
        queryFactory.update(product)
                .set(product.clickCount, product.clickCount.add(1))
                .where(product.id.eq(productId))
                .execute();
    }

    public Page<ShopProductDto> getProductsPageCondition(
            ProductSearchCondition condition, Pageable pageable) {

        QStockQuantity stockQuantity = QStockQuantity.stockQuantity1;
        QueryResults<ShopProductDto> results = queryFactory
                .select(new QShopProductDto(
                        categoryProduct.category.id,
                        category.name,
                        product.id, product.name,
                        product.salePrice, product.regularPrice,
                        product.description,
                        product.newArrival, product.best, product.discount,
                        product.productShowType,
                        productImage.id, productImage.imageFile.originalFileName, productImage.imageFile.fileName,
                        productImage.imageFile.filePath, productImage.imageFile.fileSize,
                        productImage.imageType))
                .from(product)
                .join(categoryProduct).on(categoryProduct.product.id.eq(product.id))
                .join(category).on(categoryProduct.category.id.eq(category.id))
                .leftJoin(productImage)
                .on(product.id.eq(productImage.product.id))
                .on(productImage.imageType.eq(ImageType.LIST))
                .where(
                        productNameContains(condition.getProductName()),
                        productProperty(condition.getProductProperty()),
                        firstCategoryEq(condition.getFirstCategoryId()),
                        secondCategoryEq(condition.getSecondCategoryId()),
                        JPAExpressions.select(stockQuantity.id.count()).from(stockQuantity)
                                .where(stockQuantity.product.id.eq(product.id)).gt(0l)
                )
                .orderBy(
                        getOrderSpecifiers(pageable.getSort())
                                .stream().toArray(OrderSpecifier[]::new)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ShopProductDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private List<OrderSpecifier> getOrderSpecifiers(Sort sort) {

        List<OrderSpecifier> orders = new ArrayList<>();

        log.debug("sort: {}", sort);
        if (!sort.isUnsorted()) {
            sort.stream().forEach(order -> {
                Order direction = order.isAscending() ? Order.ASC : Order.DESC;
                String property = order.getProperty();
                PathBuilder orderByExpression = new PathBuilder(Product.class, "product");
                orders.add(new OrderSpecifier(direction, orderByExpression.get(property)));
            });
        } else {
            PathBuilder orderByExpression = new PathBuilder(Product.class, "product");
            orders.add(new OrderSpecifier(Order.DESC, orderByExpression.get("id")));
        }
        return orders;
    }

    private BooleanExpression productNameContains(String productName) {
        return !isEmpty(productName)
                ? product.name.contains(productName) : null;
    }

    private BooleanExpression firstCategoryEq(Long firstCategoryId) {
        return !isEmpty(firstCategoryId)
                ? category.parent.id.eq(firstCategoryId) : null;
    }

    private BooleanExpression secondCategoryEq(Long secondCategoryId) {
        return !isEmpty(secondCategoryId)
                ? categoryProduct.category.id.eq(secondCategoryId) : null;
    }

    private BooleanExpression productProperty(String productProperty) {
        if (hasText(productProperty)) {
            if (productProperty.equals("newArrival")) {
                return product.newArrival.eq(true);
            } else if (productProperty.equals("best")) {
                return product.best.eq(true);
            } else if (productProperty.equals("discount")) {
                return product.discount.eq(true);
            }
        }
        return null;
    }

    public ShopProductDto getProductById(Long productId) {

        return queryFactory
                .select(new QShopProductDto(
                        product.id.as("productId"),
                        product.name.as("productName"),
                        product.salePrice,
                        product.regularPrice,
                        product.pointType,
                        product.pointPerPrice,
                        product.shippingFeeType,
                        product.eachShippingFee,
                        product.description,
                        product.newArrival,
                        product.best,
                        product.discount,
                        product.productShowType,
                        product.detailInfo,
                        product.shippingInfo,
                        product.exchangeReturnInfo
                ))
                .from(product)
                .where(product.id.eq(productId))
                .fetchOne();
    }
}
