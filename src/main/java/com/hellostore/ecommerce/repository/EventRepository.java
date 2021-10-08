package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.EventDto;
import com.hellostore.ecommerce.dto.EventSearchCondition;
import com.hellostore.ecommerce.dto.QEventDto;
import com.hellostore.ecommerce.entity.Event;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QEvent.event;
import static com.hellostore.ecommerce.entity.QEventImage.eventImage;
import static com.hellostore.ecommerce.entity.QExchangeRefund.exchangeRefund;
import static org.springframework.util.ObjectUtils.isEmpty;

@Repository
public class EventRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public EventRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public Event save(Event event) {
        em.persist(event);
        return event;
    }

    public EventDto getEvent(Long eventId) {

        return queryFactory.select(new QEventDto(
                event.id,
                event.title,
                event.description,
                event.eventDateA,
                event.eventDateB,
                event.content,
                eventImage.filePath,
                eventImage.fileName,
                eventImage.fileSize))
                .from(event)
                .join(eventImage).on(eventImage.event.id.eq(event.id))
                .where(event.id.eq(eventId))
                .fetchOne();
    }

    public Page<EventDto> getEvents(EventSearchCondition eventSearchCondition, Pageable pageable) {

        QueryResults<EventDto> results = queryFactory.select(
                        new QEventDto(
                                event.id,
                                event.title,
                                event.description,
                                event.eventDateA,
                                event.eventDateB,
                                eventImage.filePath,
                                eventImage.fileName,
                                eventImage.fileSize))
                .from(event)
                .join(eventImage).on(eventImage.event.id.eq(event.id))
                .where(
                        searchTextContains(eventSearchCondition.getSearchText())
                )
                .orderBy(event.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<EventDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression searchTextContains(String searchText) {
        return !isEmpty(searchText)
                ? event.title.contains(searchText)
                .or(event.content.contains(searchText)) : null;
    }

    public Event modifyEvent(Event event1) {
        queryFactory.update(event)
                .set(event.title, event1.getTitle())
                .set(event.description, event1.getDescription())
                .set(event.content, event1.getContent())
                .set(event.eventDateA, event1.getEventDateA())
                .set(event.eventDateB, event1.getEventDateB())
                .where(event.id.eq(event1.getId()))
                .execute();
        return event1;
    }

    public void removeEvents(List<Long> eventIds) {
        queryFactory.delete(event)
                .where(event.id.in(eventIds))
                .execute();
    }
}
