package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.ProductOption;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ProductOptionRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public void createProductOption(ProductOption productOption) {
        em.persist(productOption);
    }
}
