package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ProductCategoryImageDto;
import com.hellostore.ecommerce.dto.QProductCategoryImageDto;
import com.hellostore.ecommerce.entity.*;
import com.hellostore.ecommerce.enumType.ImageType;
import com.hellostore.ecommerce.enumType.ProductShowType;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QCategory.*;
import static com.hellostore.ecommerce.entity.QCategoryProduct.*;
import static com.hellostore.ecommerce.entity.QProduct.*;
import static com.hellostore.ecommerce.entity.QProductImage.*;

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

    public Page<ProductCategoryImageDto> getProductsPage(Pageable pageable) {

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
                .orderBy(product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ProductCategoryImageDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
