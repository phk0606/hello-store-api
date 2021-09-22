package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.NoticeDto;
import com.hellostore.ecommerce.dto.QNoticeDto;
import com.hellostore.ecommerce.entity.Notice;
import com.hellostore.ecommerce.entity.QNotice;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    public Page<NoticeDto> getNotices(Pageable pageable) {
        QNotice notice = QNotice.notice;
        QueryResults<NoticeDto> results
                = queryFactory.select(
                        new QNoticeDto(notice.id, notice.title, notice.content))
                .from(notice)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<NoticeDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
