package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ProductCategoryImageDto;
import com.hellostore.ecommerce.dto.QProductCategoryImageDto;
import com.hellostore.ecommerce.entity.*;
import com.hellostore.ecommerce.enumType.ImageType;
import com.querydsl.jpa.impl.JPAQueryFactory;
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

    public Product findProductById(Long id) {

        return queryFactory.selectFrom(product)
                .where(product.id.eq(id)).fetchOne();
    }

    public List<ProductCategoryImageDto> searchProducts() {

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
}
