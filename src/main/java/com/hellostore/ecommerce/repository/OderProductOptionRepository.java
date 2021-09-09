package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.OrderProductOptionDto;
import com.hellostore.ecommerce.dto.QOrderProductOptionDto;
import com.hellostore.ecommerce.entity.QOrderProductOption;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class OderProductOptionRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public OderProductOptionRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public Map<Long, List<OrderProductOptionDto>> getOrderProductOption(List<Long> orderProductIds) {
        QOrderProductOption orderProductOption = QOrderProductOption.orderProductOption;

        List<OrderProductOptionDto> orderProductOptionDtos = queryFactory.select(
                        new QOrderProductOptionDto(
                                orderProductOption.id, orderProductOption.orderProduct.id,
                                orderProductOption.optionGroupNumber, orderProductOption.optionName,
                                orderProductOption.optionValue))
                .from(orderProductOption)
                .where(orderProductOption.orderProduct.id.in(orderProductIds))
                .fetch();

        return orderProductOptionDtos.stream()
                .collect(Collectors.groupingBy(OrderProductOptionDto::getOrderProductId));
    }
}
