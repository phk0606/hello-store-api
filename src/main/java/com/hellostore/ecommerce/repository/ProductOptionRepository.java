package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ProductOptionDto;
import com.hellostore.ecommerce.dto.QProductOptionDto;
import com.hellostore.ecommerce.entity.ProductOption;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QProductOption.productOption;

@Repository
@RequiredArgsConstructor
public class ProductOptionRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ProductOption createProductOption(ProductOption productOption) {
        em.persist(productOption);
        return productOption;
    }

    public void removeProductOption(Long productId) {
        queryFactory.delete(productOption)
                .where(productOption.product.id.eq(productId))
                .execute();
    }

    public List<ProductOptionDto> getProductOptions(Long productId, Integer optionGroupNumber) {
        return queryFactory.select(
                new QProductOptionDto(productOption.id, productOption.optionGroupNumber,
                        productOption.optionName, productOption.optionValue))
                .from(productOption)
                .where(
                        productOption.product.id.eq(productId),
                        productOption.optionGroupNumber.eq(optionGroupNumber)
                        )
                .orderBy(productOption.id.asc())
                .fetch();
    }

    public ProductOption getProductOption(Long productOptionId) {

        return queryFactory.selectFrom(productOption)
                .where(productOption.id.eq(productOptionId))
                .fetchOne();
    }

    public void modifyProductOptions(ProductOption productOption1) {
        queryFactory.update(productOption)
                .set(productOption.optionName, productOption1.getOptionName())
                .set(productOption.optionValue, productOption1.getOptionValue())
                .where(productOption.id.eq(productOption1.getId()))
                .execute();

    }
}
