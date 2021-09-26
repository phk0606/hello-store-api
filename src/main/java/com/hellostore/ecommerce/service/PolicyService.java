package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.PolicyDto;
import com.hellostore.ecommerce.entity.Policy;
import com.hellostore.ecommerce.repository.PolicyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PolicyService {

    private final PolicyRepository policyRepository;

    @Transactional
    public void mergePolicy(PolicyDto policyDto) {

        if (!ObjectUtils.isEmpty(policyDto.getPolicyId())) {
            policyRepository.modifyPolicy(policyDto);
        } else {

            Policy policy = Policy.builder()
                    .defaultShippingFee(policyDto.getDefaultShippingFee())
                    .freeShippingMinPurchasePrice(policyDto.getFreeShippingMinPurchasePrice())
                    .signUpPoint(policyDto.getSignUpPoint())
                    .percentPerPurchasePrice(policyDto.getPercentPerPurchasePrice())
                    .build();
            policyRepository.createPolicy(policy);
        }
    }

    public PolicyDto getPolicy(Long policyId) {
        return policyRepository.getPolicy(policyId);
    }
}
