package com.hellostore.ecommerce.entity;

import com.hellostore.ecommerce.enumType.PaymentMethodType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    PaymentMethodType paymentMethodType;

    @Builder
    public PaymentMethod(PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }
}
