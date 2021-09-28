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

    private int defaultPoint;
    private int signUpPoint;
    private double percentPerPurchasePrice;

    @Builder
    public Policy(double defaultShippingFee, double freeShippingMinPurchasePrice, int defaultPoint, int signUpPoint, double percentPerPurchasePrice) {
        this.defaultShippingFee = defaultShippingFee;
        this.freeShippingMinPurchasePrice = freeShippingMinPurchasePrice;
        this.defaultPoint = defaultPoint;
        this.signUpPoint = signUpPoint;
        this.percentPerPurchasePrice = percentPerPurchasePrice;
    }
}
