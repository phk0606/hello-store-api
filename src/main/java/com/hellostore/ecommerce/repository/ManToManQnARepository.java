package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.*;
import com.hellostore.ecommerce.entity.ManToManQuestion;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QManToManAnswer.manToManAnswer;
import static com.hellostore.ecommerce.entity.QManToManQuestion.manToManQuestion;
import static org.springframework.util.ObjectUtils.isEmpty;

@Repository
public class ManToManQnARepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ManToManQnARepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public ManToManQuestion createManToManQuestion(ManToManQuestion manToManQuestion) {
        em.persist(manToManQuestion);
        return manToManQuestion;
    }

    public Page<ManToManQuestionDto> getManToManQuestions(
            ManToManQuestionSearchCondition manToManQuestionSearchCondition, Pageable pageable) {

        QueryResults<ManToManQuestionDto> results = queryFactory.select(
                        new QManToManQuestionDto(
                                manToManQuestion.id,
                                manToManQuestion.title,
                                manToManQuestion.content,
                                manToManQuestion.manToManQuestionType,
                                manToManQuestion.createdBy,
                                manToManQuestion.createdDate,
                                manToManAnswer.id))
                .from(manToManQuestion)
                .leftJoin(manToManAnswer)
                .on(manToManAnswer.manToManQuestion.id.eq(manToManQuestion.id))
                .where(
                        manToManQuestionContains(manToManQuestionSearchCondition.getSearchText()),
                        noAnswer(manToManQuestionSearchCondition.getNoAnswer())
                ).orderBy(manToManQuestion.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ManToManQuestionDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression manToManQuestionContains(String searchText) {
        return !isEmpty(searchText)
                ? manToManQuestion.title.contains(searchText).or(manToManQuestion.content.contains(searchText)) : null;
    }

    private BooleanExpression noAnswer(Boolean noAnswer) {
        return !isEmpty(noAnswer) && noAnswer == true
                ? manToManAnswer.id.isNull() : null;
    }

    public ManToManQnADto getManToManQnA(Long manToManQuestionId) {

        return queryFactory.select(
                new QManToManQnADto(
                        manToManQuestion.id,
                        manToManQuestion.title,
                        manToManQuestion.content,
                        manToManQuestion.manToManQuestionType,
                        manToManQuestion.createdBy,
                        manToManQuestion.createdDate,
                        manToManAnswer.id,
                        manToManAnswer.content,
                        manToManAnswer.createdBy,
                        manToManAnswer.createdDate
                ))
                .from(manToManQuestion)
                .leftJoin(manToManAnswer)
                .on(manToManAnswer.manToManQuestion.id.eq(manToManQuestion.id))
                .where(manToManQuestion.id.eq(manToManQuestionId))
                .fetchOne();
    }
}
