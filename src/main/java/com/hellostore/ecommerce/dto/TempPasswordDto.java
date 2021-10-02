package com.hellostore.ecommerce.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TempPasswordDto {

    private String personName;
    private String username;
    private String emailAddress;
    private String title;
    private String content;
    @Setter
    private String tempPassword;
}
