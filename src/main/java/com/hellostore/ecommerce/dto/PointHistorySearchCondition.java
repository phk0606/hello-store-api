package com.hellostore.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PointHistorySearchCondition {

    private String pointDateA;
    private String pointDateB;
    private String username;
}
