package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ProductQnADto;
import com.hellostore.ecommerce.dto.QProductQnADto;
import com.hellostore.ecommerce.entity.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QCategory.*;
import static com.hellostore.ecommerce.entity.QCategory.category;
import static com.hellostore.ecommerce.entity.QCategoryProduct.*;
import static com.hellostore.ecommerce.entity.QProduct.*;
import static com.hellostore.ecommerce.entity.QProductAnswer.*;
import static com.hellostore.ecommerce.entity.QProductQuestion.*;
import static org.springframework.util.ObjectUtils.isEmpty;

@Repository
public class ProductQnARepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ProductQnARepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public ProductQuestion createProductQuestion(ProductQuestion productQuestion) {
        em.persist(productQuestion);
        return productQuestion;
    }

    public void removeQuestion(ProductQnADto productQnADto) {
        queryFactory.delete(productQuestion)
                .where(
                        productQuestion.id.eq(
                                productQnADto.getProductQuestionId()))
                .execute();
    }

    public void removeAnswer(ProductQnADto productQnADto) {
        queryFactory.delete(productAnswer)
                .where(
                        productAnswer.productQuestion.id.eq(
                                productQnADto.getProductQuestionId()))
                .execute();
    }

    public void modifyQuestion(ProductQnADto productQnADto) {

        queryFactory.update(productQuestion)
                .set(productQuestion.content, productQnADto.getQuestionContent())
                .where(productQuestion.id.eq(productQnADto.getProductQuestionId()))
                .execute();
    }

    public void modifyAnswer(ProductQnADto productQnADto) {

        queryFactory.update(productAnswer)
                .set(productAnswer.content, productQnADto.getAnswerContent())
                .where(
                        productAnswer.productQuestion.id.eq(
                                productQnADto.getProductQuestionId()))
                .execute();
    }

    public ProductQuestion getProductQuestion(Long productQuestionId) {
        return queryFactory.selectFrom(productQuestion)
                .where(productQuestion.id.eq(productQuestionId))
                .fetchOne();
    }

    public ProductAnswer createProductAnswer(ProductAnswer productAnswer) {
        em.persist(productAnswer);
        return productAnswer;
    }

    public Page<ProductQnADto> getProductQnA(Long productId, Pageable pageable) {

        QueryResults<ProductQnADto> results = queryFactory.select(
                        new QProductQnADto(productQuestion.product.id,
                                productQuestion.id, productQuestion.createdBy,
                                productQuestion.content, productQuestion.createdDate,
                                productAnswer.id, productAnswer.createdBy,
                                productAnswer.content, productAnswer.createdDate,
                                product.name, category.name
                                ))
                .from(productQuestion)
                .leftJoin(productAnswer)
                .on(productQuestion.id.eq(productAnswer.productQuestion.id))
                .join(product)
                .on(product.id.eq(productQuestion.product.id))
                .join(categoryProduct)
                .on(categoryProduct.product.id.eq(productQuestion.product.id))
                .join(category)
                .on(category.id.eq(categoryProduct.category.id))
                .where(
                        productIdEq(productId)
                )
                .orderBy(productQuestion.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ProductQnADto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression productIdEq(Long productId) {
        return !isEmpty(productId)
                ? productQuestion.product.id.eq(productId) : null;
    }
}
