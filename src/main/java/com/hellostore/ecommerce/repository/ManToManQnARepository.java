package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.*;
import com.hellostore.ecommerce.entity.ManToManAnswer;
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

    public ManToManQuestion getManToManQuestion(Long manToManQuestionId) {
        return em.find(ManToManQuestion.class, manToManQuestionId);
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
                        noAnswer(manToManQuestionSearchCondition.getNoAnswer()),
                        usernameEq(manToManQuestionSearchCondition.getUsername())
                ).orderBy(manToManQuestion.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ManToManQuestionDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression usernameEq(String username) {
        return !isEmpty(username)
                ? manToManQuestion.createdBy.eq(username) : null;
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

    public void modifyManToManQuestion(ManToManQuestionDto manToManQuestionDto) {

        queryFactory.update(manToManQuestion)
                .set(manToManQuestion.title, manToManQuestionDto.getManToManQuestionTitle())
                .set(manToManQuestion.content, manToManQuestionDto.getManToManQuestionContent())
                .set(manToManQuestion.manToManQuestionType, manToManQuestionDto.getManToManQuestionType())
                .where(manToManQuestion.id.eq(manToManQuestionDto.getManToManQuestionId()))
                .execute();
    }

    public void modifyAnswer(ManToManQnADto manToManQnADto) {

        queryFactory.update(manToManAnswer)
                .set(manToManAnswer.content, manToManQnADto.getManToManAnswerContent())
                .where(manToManAnswer.id.eq(manToManQnADto.getManToManAnswerId()))
                .execute();
    }

    public void createAnswer(ManToManAnswer manToManAnswer) {
        em.persist(manToManAnswer);
    }

    public void removeManToManQuestion(ManToManQnADto manToManQnADto) {
        queryFactory.delete(manToManQuestion)
                .where(manToManQuestion.id.eq(manToManQnADto.getManToManQuestionId()))
                .execute();
    }

    public void removeManToManAnswer(ManToManQnADto manToManQnADto) {
        queryFactory.delete(manToManAnswer)
                .where(
                        manToManQuestionIdEq(manToManQnADto.getManToManQuestionId()),
                        manToManAnswerIdEq(manToManQnADto.getManToManAnswerId())
                ).execute();
    }

    private BooleanExpression manToManQuestionIdEq(Long manToManQuestionId) {
        return !isEmpty(manToManQuestionId)
                ? manToManAnswer.manToManQuestion.id.eq(manToManQuestionId) : null;
    }

    private BooleanExpression manToManAnswerIdEq(Long manToManAnswerId) {
        return !isEmpty(manToManAnswerId)
                ? manToManAnswer.id.eq(manToManAnswerId) : null;
    }
}
