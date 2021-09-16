package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Address {

    @Column(length = 5)
    private String zoneCode;

    @Column(length = 200)
    private String address;

    @Column(length = 200)
    private String roadAddress;

    @Column(length = 100)
    private String detailAddress;
}
