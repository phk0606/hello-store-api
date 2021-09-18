package com.hellostore.ecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class UserSearchCondition {

    private String username;
    private String name;
    private String userJoinDateA;
    private String userJoinDateB;

    private Integer purchasePriceMin;
    private Integer purchasePriceMax;

    boolean activated;
}
