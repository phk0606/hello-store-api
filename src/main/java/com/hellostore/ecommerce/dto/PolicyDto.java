package com.hellostore.ecommerce.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class PolicyDto {

    private Long policyId;
    private double defaultShippingFee;
    private double freeShippingMinPurchasePrice;

    private int signUpPoint;
    private double percentPerPurchasePrice;

    @QueryProjection
    public PolicyDto(Long policyId, double defaultShippingFee, double freeShippingMinPurchasePrice, int signUpPoint, double percentPerPurchasePrice) {
        this.policyId = policyId;
        this.defaultShippingFee = defaultShippingFee;
        this.freeShippingMinPurchasePrice = freeShippingMinPurchasePrice;
        this.signUpPoint = signUpPoint;
        this.percentPerPurchasePrice = percentPerPurchasePrice;
    }
}
