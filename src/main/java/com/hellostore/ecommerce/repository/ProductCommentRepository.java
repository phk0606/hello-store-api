package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ProductCommentDto;
import com.hellostore.ecommerce.dto.QProductCommentDto;
import com.hellostore.ecommerce.entity.ProductComment;
import com.hellostore.ecommerce.entity.QProduct;
import com.hellostore.ecommerce.entity.QProductComment;
import com.hellostore.ecommerce.entity.QProductCommentImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QProduct.*;
import static com.hellostore.ecommerce.entity.QProductComment.*;
import static com.hellostore.ecommerce.entity.QProductCommentImage.*;

@Repository
public class ProductCommentRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ProductCommentRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public ProductComment save(ProductComment productComment) {
        em.persist(productComment);
        return productComment;
    }

    public List<ProductCommentDto> getProductComments(Long productId) {

        return queryFactory.select(
                    new QProductCommentDto(
                        productComment.id, productComment.user.username,
                         productComment.content, productComment.grade,
                            productCommentImage.fileName, productComment.createdDate
                    )
                )
                .from(productComment)
                .join(product).on(product.id.eq(productComment.orderProduct.product.id))
                .leftJoin(productCommentImage)
                .on(productCommentImage.productComment.id.eq(productComment.id))
                .where(productComment.orderProduct.product.id.eq(productId))
                .orderBy(productComment.id.desc())
                .fetch();
    }
}
