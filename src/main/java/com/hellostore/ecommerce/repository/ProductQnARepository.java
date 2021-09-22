package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ProductQnADto;
import com.hellostore.ecommerce.dto.QProductQnADto;
import com.hellostore.ecommerce.entity.ProductQuestion;
import com.hellostore.ecommerce.entity.QProductAnswer;
import com.hellostore.ecommerce.entity.QProductQuestion;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    public Page<ProductQnADto> getProductQnA(Long productId, Pageable pageable) {

        QProductQuestion productQuestion = QProductQuestion.productQuestion;
        QProductAnswer productAnswer = QProductAnswer.productAnswer;

        QueryResults<ProductQnADto> results = queryFactory.select(
                        new QProductQnADto(productQuestion.product.id,
                                productQuestion.id, productQuestion.createdBy,
                                productQuestion.content, productQuestion.createdDate,
                                productAnswer.id, productAnswer.createdBy,
                                productAnswer.content, productAnswer.createdDate))
                .from(productQuestion)
                .leftJoin(productAnswer)
                .on(productQuestion.id.eq(productAnswer.productQuestion.id))
                .where(productQuestion.product.id.eq(productId))
                .orderBy(productQuestion.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ProductQnADto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
