package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.Category;
import com.hellostore.ecommerce.entity.QCategory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryDslRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public List<Category> getCategories() {
        QCategory parent = new QCategory("parent");
        QCategory child = new QCategory("child");

        return queryFactory.selectFrom(parent)
                .distinct()
                .leftJoin(parent.children, child)
                .fetchJoin()
                .where(parent.parent.isNull())
                .orderBy(parent.sequence.asc(), child.sequence.asc())
                .fetch();
    }

    public List<Category> getCategory(Long parentId) {
        QCategory category = QCategory.category;

        BooleanBuilder builder = new BooleanBuilder();

        if(parentId != null) {
            builder.and(category.parent.id.eq(parentId));
        } else {
            builder.and(category.parent.isNull());
        }

        return queryFactory.selectFrom(category)
                .from(category)
                .where(builder)
                .orderBy(category.sequence.asc())
                .fetch();
    }

//    public Tuple getCategoryForProduct(Long productId) {
//        QCategory category = QCategory.category;
//        QCategoryProduct categoryProduct = QCategoryProduct.categoryProduct;
//        QProduct product = QProduct.product;
//
//        Tuple fetch = queryFactory.select(category, category.parent)
//                .from(category)
//                .join(categoryProduct).on(category.id.eq(categoryProduct.category.id))
//                .join(product).on(categoryProduct.product.id.eq(product.id))
//                .where(product.id.eq(productId))
//                .fetchOne();
//
//        return fetch;
//    }

    public Category getCategoryOne(Long id) {
        QCategory category = QCategory.category;

        return queryFactory.selectFrom(category)
                .where(category.id.eq(id))
                .fetchOne();
    }

    public Integer getCategoryMaxSequence(Long categoryId, Long parentId) {
        QCategory productCategory = QCategory.category;

        Integer maxSequence = 0;
        if(parentId == null && categoryId == null) {

            maxSequence = queryFactory.select(productCategory.sequence.max())
                    .from(productCategory)
                    .where(productCategory.parent.isNull())
                    .fetchOne();
        } else if (parentId != null && categoryId == null) {

            maxSequence = queryFactory.select(productCategory.sequence.max())
                    .from(productCategory)
                    .where(productCategory.parent.id.eq(parentId))
                    .fetchOne();
            maxSequence = maxSequence == null ? 0 : maxSequence;
        }
        return maxSequence;
    }

    public void createCategory(Category category) {
        em.persist(category);
    }

    public void modifyCategory(Category category) {
        QCategory qProductCategory = QCategory.category;
        queryFactory.update(qProductCategory)
                .where(qProductCategory.id.eq(category.getId()))
                .set(qProductCategory.name, category.getName())
                .set(qProductCategory.showYn, category.getShowYn())
                .execute();
    }

    public void deleteCategory(Category category) {
        QCategory qCategory = QCategory.category;
        queryFactory.delete(qCategory)
                .where(qCategory.id.eq(category.getId()))
                .execute();
    }


}
