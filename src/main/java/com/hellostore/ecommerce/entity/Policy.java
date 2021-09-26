package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_id")
    private Long id;

    private double defaultShippingFee;
    private double freeShippingMinPurchasePrice;

    private int signUpPoint;
    private double percentPerPurchasePrice;

    @Builder
    public Policy(double defaultShippingFee, double freeShippingMinPurchasePrice, int signUpPoint, double percentPerPurchasePrice) {
        this.defaultShippingFee = defaultShippingFee;
        this.freeShippingMinPurchasePrice = freeShippingMinPurchasePrice;
        this.signUpPoint = signUpPoint;
        this.percentPerPurchasePrice = percentPerPurchasePrice;
    }
}
