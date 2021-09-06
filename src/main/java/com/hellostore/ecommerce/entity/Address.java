package com.hellostore.ecommerce.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Address {

    @Column(length = 5)
    private String zoneCode;

    @Column(length = 200)
    private String address;

    @Column(length = 100)
    private String detailAddress;
}
