package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.CategoryProduct;
import com.hellostore.ecommerce.entity.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CategoryProductRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public CategoryProduct createCategoryProduct(CategoryProduct categoryProduct) {
        em.persist(categoryProduct);
        return categoryProduct;
    }
}
