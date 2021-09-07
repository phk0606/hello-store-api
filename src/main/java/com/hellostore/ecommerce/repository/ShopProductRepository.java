package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.*;
import com.hellostore.ecommerce.entity.QProductOption;
import com.hellostore.ecommerce.enumType.ImageType;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QCategory.category;
import static com.hellostore.ecommerce.entity.QCategoryProduct.categoryProduct;
import static com.hellostore.ecommerce.entity.QProduct.product;
import static com.hellostore.ecommerce.entity.QProductImage.productImage;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class ShopProductRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ShopProductRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public Page<ShopProductDto> getProductsPageCondition(
            ProductSearchCondition condition, Pageable pageable) {

        QueryResults<ShopProductDto> results = queryFactory
                .select(new QShopProductDto(
                        categoryProduct.category.id,
                        category.name,
                        product.id, product.name,
                        product.salePrice, product.regularPrice,
                        product.description,
                        product.newArrival, product.best, product.discount,
                        product.productShowType,
                        productImage.id, productImage.originalFileName, productImage.fileName,
                        productImage.filePath, productImage.fileSize,
                        productImage.imageType))
                .from(product)
                .join(categoryProduct).on(categoryProduct.product.id.eq(product.id))
                .join(category).on(categoryProduct.category.id.eq(category.id))
                .leftJoin(productImage)
                .on(product.id.eq(productImage.product.id))
                .on(productImage.imageType.eq(ImageType.LIST))
                .where(
                        productProperty(condition.getProductProperty())
                )
                .orderBy(product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ShopProductDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
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
