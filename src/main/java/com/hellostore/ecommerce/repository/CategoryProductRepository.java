package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.CategoryProduct;
import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.entity.QCategoryProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.hellostore.ecommerce.entity.QCategoryProduct.*;

@Repository
public class CategoryProductRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public CategoryProductRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public CategoryProduct createCategoryProduct(CategoryProduct categoryProduct) {
        em.persist(categoryProduct);
        return categoryProduct;
    }

    public void removeCategoryProduct(Long productId) {
        queryFactory.delete(categoryProduct)
                .where(categoryProduct.product.id.eq(productId))
                .execute();
    }
}
