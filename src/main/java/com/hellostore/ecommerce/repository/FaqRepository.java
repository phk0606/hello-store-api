package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.FaqDto;
import com.hellostore.ecommerce.dto.FaqSearchCondition;
import com.hellostore.ecommerce.dto.QFaqDto;
import com.hellostore.ecommerce.entity.Faq;
import com.hellostore.ecommerce.enumType.FaqType;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QFaq.faq;
import static org.springframework.util.ObjectUtils.isEmpty;

@Repository
public class FaqRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public FaqRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public Faq save(Faq faq) {
        em.persist(faq);
        return faq;
    }

    public Page<FaqDto> getFaqs(FaqSearchCondition faqSearchCondition, Pageable pageable) {

        QueryResults<FaqDto> results = queryFactory.select(
                new QFaqDto(faq.id, faq.question, faq.answer, faq.faqType))
                .from(faq)
                .where(
                        faqContains(faqSearchCondition.getSearchText()),
                        faqTypeEq(faqSearchCondition.getFaqType())
                ).orderBy(faq.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<FaqDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression faqContains(String searchText) {
        return !isEmpty(searchText)
                ? faq.question.contains(searchText).or(faq.answer.contains(searchText)) : null;
    }

    private BooleanExpression faqTypeEq(FaqType faqType) {
        return !isEmpty(faqType)
                ? faq.faqType.eq(faqType) : null;
    }

    public void modifyFaq(FaqDto faqDto) {
        queryFactory.update(faq)
                .set(faq.question, faqDto.getQuestion())
                .set(faq.answer, faqDto.getAnswer())
                .set(faq.faqType, faqDto.getFaqType())
                .where(faq.id.eq(faqDto.getFaqId()))
                .execute();
    }

    public void removeFaq(FaqDto faqDto) {
        queryFactory.delete(faq)
                .where(faq.id.eq(faqDto.getFaqId()))
                .execute();
    }

    public FaqDto getFaq(Long faqId) {
        return queryFactory.select(
                new QFaqDto(faq.id, faq.question, faq.answer, faq.faqType))
                .from(faq)
                .where(faq.id.eq(faqId))
                .fetchOne();
    }
}
