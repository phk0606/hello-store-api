package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ProductCategoryImageDto;
import com.hellostore.ecommerce.dto.ProductSearchCondition;
import com.hellostore.ecommerce.dto.QProductCategoryImageDto;
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

import javax.persistence.EntityManager;
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

    public Product getProductById(Long id) {

        return queryFactory.selectFrom(product)
                .where(product.id.eq(id)).fetchOne();
    }

    public List<ProductCategoryImageDto> getProducts() {

        return queryFactory
                .select(new QProductCategoryImageDto(
                        categoryProduct.category.id,
                        category.name,
                        product.id, product.name,
                        product.salePrice, product.productShowType, product.clickCount,
                        product.createdDate,
                        product.lastModifiedDate, product.createdBy,
                        productImage.id, productImage.originalFileName, productImage.fileName,
                        productImage.filePath, productImage.fileSize,
                        productImage.imageType))
                .from(product)
                .join(categoryProduct).on(categoryProduct.product.id.eq(product.id))
                .join(category).on(categoryProduct.category.id.eq(category.id))
                .leftJoin(productImage)
                    .on(product.id.eq(productImage.product.id))
                    .on(productImage.imageType.eq(ImageType.LIST))
                .fetch();
    }

    public Page<ProductCategoryImageDto> getProductsPage(
            ProductSearchCondition condition, Pageable pageable) {

        QueryResults<ProductCategoryImageDto> results = queryFactory
                .select(new QProductCategoryImageDto(
                        categoryProduct.category.id,
                        category.name,
                        product.id, product.name,
                        product.salePrice, product.productShowType, product.clickCount,
                        product.createdDate,
                        product.lastModifiedDate, product.createdBy,
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
                        nameLike(condition.getProductName()),
                        firstCategoryEq(condition.getFirstCategoryId()),
                        secondCategoryEq(condition.getSecondCategoryId()),
                        salePriceMin(condition.getSalePriceMin()),
                        salePriceMax(condition.getSalePriceMax()),
                        productShowTypeIn(condition.getProductShowTypes())
                )
                .orderBy(product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ProductCategoryImageDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression nameLike(String productName) {
        return hasText(productName) ? product.name.like(productName) : null;
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
}
