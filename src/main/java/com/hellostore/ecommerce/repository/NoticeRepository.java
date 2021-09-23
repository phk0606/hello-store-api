package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.NoticeDto;
import com.hellostore.ecommerce.dto.NoticeSearchCondition;
import com.hellostore.ecommerce.dto.QNoticeDto;
import com.hellostore.ecommerce.entity.Notice;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QNotice.notice;
import static org.springframework.util.ObjectUtils.isEmpty;

@Repository
public class NoticeRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public NoticeRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public Notice save(Notice notice) {
        em.persist(notice);
        return notice;
    }

    public void modifyNotice(NoticeDto noticeDto) {
        queryFactory.update(notice)
                .set(notice.title, noticeDto.getTitle())
                .set(notice.content, noticeDto.getContent())
                .set(notice.important, noticeDto.isImportant())
                .where(notice.id.eq(noticeDto.getNoticeId()))
                .execute();
    }

    public void removeNotice(NoticeDto noticeDto) {
        queryFactory.delete(notice)
                .where(notice.id.eq(noticeDto.getNoticeId()))
                .execute();
    }

    public Page<NoticeDto> getNotices(NoticeSearchCondition noticeSearchCondition,
                                      Pageable pageable) {

        QueryResults<NoticeDto> results
                = queryFactory.select(
                        new QNoticeDto(
                                notice.id, notice.title,
                                notice.content, notice.important, notice.createdDate))
                .from(notice)
                .where(
                        noticeTitleContains(noticeSearchCondition.getTitle()),
                        noticeContentContains(noticeSearchCondition.getContent())
                )
                .orderBy(notice.important.desc(), notice.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<NoticeDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression noticeTitleContains(String title) {
        return !isEmpty(title)
                ? notice.title.contains(title) : null;
    }

    private BooleanExpression noticeContentContains(String content) {
        return !isEmpty(content)
                ? notice.content.contains(content) : null;
    }

    public NoticeDto getNotice(Long noticeId) {
        return queryFactory.select(
                new QNoticeDto(notice.id, notice.title,
                        notice.content, notice.important, notice.createdDate))
                .from(notice)
                .where(notice.id.eq(noticeId))
                .fetchOne();
    }
}
