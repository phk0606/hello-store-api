package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.ProductCategory;
import com.hellostore.ecommerce.entity.QProductCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductCategoryDslRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public List<ProductCategory> getProductCategories() {
        QProductCategory parent = new QProductCategory("parent");
        QProductCategory child = new QProductCategory("child");

        return queryFactory.selectFrom(parent)
                .distinct()
                .leftJoin(parent.children, child)
                .fetchJoin()
                .where(parent.parent.isNull())
                .orderBy(parent.sequence.asc(), child.sequence.asc())
                .fetch();
    }

    public void createProductCategory(ProductCategory productCategory) {
        em.persist(productCategory);
    }
}
