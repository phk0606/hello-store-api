package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.PaymentMethod;
import com.hellostore.ecommerce.enumType.PaymentMethodType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.hellostore.ecommerce.entity.QPaymentMethod.paymentMethod;

@Repository
public class PaymentMethodRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public PaymentMethodRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethod) {
        em.persist(paymentMethod);
        return paymentMethod;
    }

    public void removePaymentMethod() {
        queryFactory.delete(paymentMethod).execute();
    }

    public List<PaymentMethodType> getPaymentMethodTypes() {
        return queryFactory.select(paymentMethod.paymentMethodType)
                .from(paymentMethod)
                .fetch();
    }
}
