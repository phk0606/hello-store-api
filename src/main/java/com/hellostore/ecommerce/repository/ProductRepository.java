package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ProductCategoryImageDto;
import com.hellostore.ecommerce.dto.QProductCategoryImageDto;
import com.hellostore.ecommerce.entity.*;
import com.hellostore.ecommerce.enumType.ImageType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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
        QProduct product = QProduct.product;

        return queryFactory.selectFrom(product)
                .where(product.id.eq(id)).fetchOne();
    }

    public List<ProductCategoryImageDto> searchProducts() {
        QProduct product = QProduct.product;
        QCategoryProduct categoryProduct = QCategoryProduct.categoryProduct;
        QCategory category = QCategory.category;
        QProductImage productImage = QProductImage.productImage;

        return queryFactory
                .select(new QProductCategoryImageDto(
                        categoryProduct.category.id,
                        category.name,
                        product.id, product.name,
                        product.salePrice, product.productShowType.stringValue(), product.clickCount,
                        product.createdDate,
                        product.lastModifiedDate, product.createdBy,
                        productImage.id, productImage.originalFileName, productImage.fileName,
                        productImage.filePath, productImage.fileSize,
                        productImage.imageType.stringValue()))
                .from(product)
                .join(categoryProduct).on(categoryProduct.product.id.eq(product.id))
                .join(category).on(categoryProduct.category.id.eq(category.id))
                .join(productImage).on(product.id.eq(productImage.product.id))
                .where(productImage.imageType.eq(ImageType.LIST))
                .fetch();
    }
}
