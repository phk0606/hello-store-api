package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.entity.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public Long createProduct(Product product) {
        em.persist(product);
        return product.getId();
    }

    public Product findProductById(Long id) {
        QProduct product = QProduct.product;

        return queryFactory.selectFrom(product)
                .where(product.id.eq(id)).fetchOne();
    }

}
