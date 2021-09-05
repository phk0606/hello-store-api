package com.hellostore.ecommerce.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class RefreshToken {

    @Id
    private String username;
    private String token;

    public RefreshToken updateValue(String token) {
        this.token = token;
        return this;
    }

    @Builder
    public RefreshToken(String username, String token) {
        this.username = username;
        this.token = token;
    }
}
