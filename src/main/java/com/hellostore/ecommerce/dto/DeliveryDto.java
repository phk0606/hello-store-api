package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeliveryDto {

    private String recipientName;

    private String phoneNumber;

    private String requirement;

    private Address address;
}
