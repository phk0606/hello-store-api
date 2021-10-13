package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.*;
import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.enumType.ImageType;
import com.hellostore.ecommerce.enumType.ProductShowType;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.hellostore.ecommerce.entity.QCategory.category;
import static com.hellostore.ecommerce.entity.QCategoryProduct.categoryProduct;
import static com.hellostore.ecommerce.entity.QProduct.product;
import static com.hellostore.ecommerce.entity.QProductImage.productImage;
import static org.springframework.util.ObjectUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class ProductRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ProductRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public Product createProduct(Product product) {
        em.persist(product);
        return product;
    }

    public List<ProductSelectDto> getProductsByCategoryId(Long categoryId) {
        return queryFactory.select(new QProductSelectDto(product.id, product.name))
                .from(categoryProduct)
                .join(product).on(product.id.eq(categoryProduct.product.id))
                .where(categoryProduct.category.id.eq(categoryId))
                .fetch();
    }

    public List<ProductSelectDto> getProducts() {

        return queryFactory.select(new QProductSelectDto(product.id, product.name))
                .from(product)
                .orderBy(product.name.asc())
                .fetch();
    }

    public void removeProduct(Long productId) {

        queryFactory.delete(product)
                .where(product.id.eq(productId))
                .execute();
    }

    public void modifyProductShowType(List<Long> productIds, ProductShowType productShowType) {

        queryFactory.update(product)
                .set(product.productShowType, productShowType)
                .where(product.id.in(productIds))
                .execute();
    }

    public Product modifyProduct(Product productEntity) {

        queryFactory.update(product)
                .set(product.name, productEntity.getName())
                .set(product.salePrice, productEntity.getSalePrice())
                .set(product.regularPrice, productEntity.getRegularPrice())
//                .set(product.stockQuantity, productEntity.getStockQuantity())
                .set(product.maxPurchaseQuantity, productEntity.getMaxPurchaseQuantity())
                .set(product.pointType, productEntity.getPointType())
                .set(product.pointPerPrice, productEntity.getPointPerPrice())
                .set(product.shippingFeeType, productEntity.getShippingFeeType())
                .set(product.eachShippingFee, productEntity.getEachShippingFee())
                .set(product.newArrival, productEntity.getNewArrival())
                .set(product.best, productEntity.getBest())
                .set(product.discount, productEntity.getDiscount())
                .set(product.description, productEntity.getDescription())
                .set(product.detailInfo, productEntity.getDetailInfo())
                .set(product.shippingInfo, productEntity.getShippingInfo())
                .set(product.exchangeReturnInfo, productEntity.getExchangeReturnInfo())
                .set(product.productShowType, productEntity.getProductShowType())
                .set(product.lastModifiedDate, LocalDateTime.now())
                .where(product.id.eq(productEntity.getId()))
                .execute();

        return productEntity;
    }

    public Product getProduct(Long productId) {

        return queryFactory.selectFrom(product)
                .where(product.id.eq(productId))
                .fetchOne();
    }

    public ProductModifyDto getProductById(Long productId) {

        return queryFactory
                .select(new QProductModifyDto(
                        product.id.as("productId"),
                        category.parent.id.as("firstCategoryId"),
                        categoryProduct.category.id.as("secondCategoryId"),
                        product.name.as("productName"),
                        product.salePrice,
                        product.regularPrice,
                        product.maxPurchaseQuantity,
                        product.pointType,
                        product.pointPerPrice,
                        product.shippingFeeType,
                        product.eachShippingFee,
                        product.newArrival,
                        product.best,
                        product.discount,
                        product.description,
                        product.detailInfo,
                        product.shippingInfo,
                        product.exchangeReturnInfo,
                        product.productShowType
                        ))
                .from(product)
                .join(categoryProduct).on(categoryProduct.product.id.eq(product.id))
                .join(category).on(categoryProduct.category.id.eq(category.id))
                .where(product.id.eq(productId))
                .fetchOne();
    }

    public Page<ProductListDto> getProductsPage(
            ProductSearchCondition condition, Pageable pageable) {

        QueryResults<ProductListDto> results = queryFactory
                .select(new QProductListDto(
                        categoryProduct.category.id,
                        category.name,
                        product.id, product.name,
                        product.salePrice, product.productShowType, product.clickCount,
                        product.createdDate,
                        product.lastModifiedDate, product.createdBy,
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
                        nameLike(condition.getProductName()),
                        firstCategoryEq(condition.getFirstCategoryId()),
                        secondCategoryEq(condition.getSecondCategoryId()),
                        salePriceMin(condition.getSalePriceMin()),
                        salePriceMax(condition.getSalePriceMax()),
                        productShowTypeIn(condition.getProductShowTypes()),
                        productRegistryDateA(condition.getProductRegistryDateA()),
                        productRegistryDateB(condition.getProductRegistryDateB())
                )
                .orderBy(product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ProductListDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression nameLike(String productName) {
        return hasText(productName) ? product.name.contains(productName) : null;
    }

    private BooleanExpression firstCategoryEq(Long firstCategoryId) {
        return !isEmpty(firstCategoryId)
                ? category.parent.id.eq(firstCategoryId) : null;
    }

    private BooleanExpression secondCategoryEq(Long secondCategoryId) {
        return !isEmpty(secondCategoryId)
                ? categoryProduct.category.id.eq(secondCategoryId) : null;
    }

    private BooleanExpression salePriceMin(Integer salePriceMin) {
        return !isEmpty(salePriceMin) ? product.salePrice.goe(salePriceMin) : null;
    }

    private BooleanExpression salePriceMax(Integer salePriceMax) {
        return !isEmpty(salePriceMax) ? product.salePrice.loe(salePriceMax) : null;
    }

    private BooleanExpression productShowTypeIn(List<ProductShowType> productShowTypes) {
        return productShowTypes.size() > 0 ? product.productShowType.in(productShowTypes) : null;
    }

    private BooleanExpression productRegistryDateA(String productRegistryDateA) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return StringUtils.hasText(productRegistryDateA)
                ? product.createdDate.goe(LocalDateTime.parse(productRegistryDateA + " 00:00:00", formatter)) : null;
    }

    private BooleanExpression productRegistryDateB(String productRegistryDateB) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return StringUtils.hasText(productRegistryDateB)
                ? product.createdDate.loe(LocalDateTime.parse(productRegistryDateB + " 23:59:59", formatter)) : null;
    }

    public Long getProductCount() {
        return queryFactory.select(product.id.count())
                .from(product)
                .fetchOne();
    }
}
