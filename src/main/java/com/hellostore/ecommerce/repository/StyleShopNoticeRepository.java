package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.QStyleShopNoticeDto;
import com.hellostore.ecommerce.dto.StyleShopNoticeDto;
import com.hellostore.ecommerce.entity.QStyleShopNotice;
import com.hellostore.ecommerce.entity.StyleShopNotice;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.Optional;

import static com.hellostore.ecommerce.entity.QStyleShopNotice.*;

@Repository
public class StyleShopNoticeRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public StyleShopNoticeRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public Optional<StyleShopNotice> getStyleShopNotice(Long id) {

        return Optional.ofNullable(queryFactory.selectFrom(styleShopNotice)
                .where(styleShopNotice.id.eq(id))
                .fetchOne());
    }

    public StyleShopNotice createStyleShopNotice(StyleShopNotice styleShopNotice) {
        em.persist(styleShopNotice);
        return styleShopNotice;
    }

    public void updateStyleShopNotice(StyleShopNoticeDto styleShopNoticeDto) {

        queryFactory.update(styleShopNotice)
                .set(styleShopNotice.content, styleShopNoticeDto.getContent())
                .where(styleShopNotice.id.eq(styleShopNoticeDto.getId()))
                .execute();
    }

    public void updateStyleShopNotice(Long categoryId, String content) {

        queryFactory.update(styleShopNotice)
                .set(styleShopNotice.content, content)
                .where(styleShopNotice.category.id.eq(categoryId))
                .execute();
    }

    public StyleShopNoticeDto getCategoryNotice(Long categoryId) {
        QStyleShopNotice styleShopNotice = QStyleShopNotice.styleShopNotice;
        return queryFactory.select(
                new QStyleShopNoticeDto(styleShopNotice.id, styleShopNotice.content))
                .from(styleShopNotice)
                .where(styleShopNotice.category.id.eq(categoryId))
                .fetchOne();
    }
}
