package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.entity.PaymentMethod;
import com.hellostore.ecommerce.enumType.PaymentMethodType;
import com.hellostore.ecommerce.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    @Transactional
    public List<PaymentMethodType> removeAndCreatePaymentMethod(List<PaymentMethodType> paymentMethodTypes) {

        paymentMethodRepository.removePaymentMethod();

        for (PaymentMethodType paymentMethodType : paymentMethodTypes) {

            log.debug("paymentMethodType: {}", paymentMethodType);
            PaymentMethod paymentMethod
                    = PaymentMethod.builder().paymentMethodType(paymentMethodType).build();
            paymentMethodRepository.createPaymentMethod(paymentMethod);
        }

        return paymentMethodRepository.getPaymentMethodTypes();
    }

    public List<PaymentMethodType> getPaymentMethodTypes() {
        return paymentMethodRepository.getPaymentMethodTypes();
    }
}
