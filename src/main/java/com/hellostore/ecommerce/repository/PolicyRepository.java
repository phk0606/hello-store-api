package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.PolicyDto;
import com.hellostore.ecommerce.dto.QPolicyDto;
import com.hellostore.ecommerce.entity.Policy;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.hellostore.ecommerce.entity.QPolicy.policy;
import static org.springframework.util.ObjectUtils.isEmpty;

@Repository
public class PolicyRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public PolicyRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public Policy createPolicy(Policy policy) {
        em.persist(policy);
        return policy;
    }

    public PolicyDto getPolicy(Long policyId) {
        return queryFactory.select(
                new QPolicyDto(policy.id,
                        policy.defaultShippingFee,
                        policy.freeShippingMinPurchasePrice,
                        policy.signUpPoint,
                        policy.percentPerPurchasePrice))
                .from(policy)
                .where(policyIdEq(policyId))
                .fetchOne();
    }

    private BooleanExpression policyIdEq(Long policyId) {
        return !isEmpty(policyId)
                ? policy.id.eq(policyId) : null;
    }

    public void modifyPolicy(PolicyDto policyDto) {
        queryFactory.update(policy)
                .set(policy.defaultShippingFee, policyDto.getDefaultShippingFee())
                .set(policy.freeShippingMinPurchasePrice, policyDto.getFreeShippingMinPurchasePrice())
                .set(policy.signUpPoint, policyDto.getSignUpPoint())
                .set(policy.percentPerPurchasePrice, policyDto.getPercentPerPurchasePrice())
                .where(policy.id.eq(policyDto.getPolicyId()))
                .execute();
    }
}
