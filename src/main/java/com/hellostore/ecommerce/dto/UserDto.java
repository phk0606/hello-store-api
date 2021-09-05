package com.hellostore.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hellostore.ecommerce.entity.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @Size(min = 2, max = 50)
    private String email;

    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @NotNull
    @Size(min = 10, max = 11)
    private String phoneNumber;

    @NotNull
    @Size(max = 5)
    private String zoneCode;

    @NotNull
    @Size(min = 10, max = 200)
    private String address;

    @NotNull
    @Size(max = 100)
    private String detailAddress;

    public UserDto(String username) {
        this.username = username;
    }

    public static UserDto of(User user) {
        return new UserDto(user.getUsername());
    }
}
