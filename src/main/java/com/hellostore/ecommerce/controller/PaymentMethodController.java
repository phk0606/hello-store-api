package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.PaymentMethodDto;
import com.hellostore.ecommerce.dto.PaymentMethodTypeDto;
import com.hellostore.ecommerce.enumType.PaymentMethodType;
import com.hellostore.ecommerce.service.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paymentMethod")
@RequiredArgsConstructor
@Slf4j
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @PutMapping("/modifyPaymentMethod")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<PaymentMethodType> modifyPaymentMethod(@RequestBody PaymentMethodDto paymentMethodDto) {
        log.debug("paymentMethodDto: {}", paymentMethodDto);
        return paymentMethodService.removeAndCreatePaymentMethod(paymentMethodDto.getPaymentMethodTypes());
    }

    @GetMapping("/getPaymentMethodTypes")
    public List<PaymentMethodType> getPaymentMethodTypes() {
        return paymentMethodService.getPaymentMethodTypes();
    }

    @GetMapping("/getPaymentMethodTypesWithValues")
    public List<PaymentMethodTypeDto> getPaymentMethodTypesWithValues() {
        return paymentMethodService.getPaymentMethodTypesWithValues();
    }
}
