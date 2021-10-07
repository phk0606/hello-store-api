package com.hellostore.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hellostore.ecommerce.entity.User;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDto {

    private Long userNo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdDate;

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    private String newPassword;

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
    private String roadAddress;

    @NotNull
    @Size(min = 10, max = 200)
    private String address;

    @NotNull
    @Size(max = 100)
    private String detailAddress;

    private Integer purchasePriceSum;
    private Integer pointSum;
    @Setter
    private int signUpPoint;

    public UserDto(Long userNo, String username, String name, String email, String phoneNumber,
                   String zoneCode, String roadAddress, String address, String detailAddress) {
        this.userNo = userNo;
        this.username = username;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.zoneCode = zoneCode;
        this.roadAddress = roadAddress;
        this.address = address;
        this.detailAddress = detailAddress;
    }

    public static UserDto of(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress().getZoneCode(),
                user.getAddress().getRoadAddress(),
                user.getAddress().getAddress(),
                user.getAddress().getDetailAddress());
    }

    @QueryProjection
    public UserDto(Long userNo, String username, String name,
                   LocalDateTime createdDate, Integer purchasePriceSum, Integer pointSum
                   ) {
        this.userNo = userNo;
        this.username = username;
        this.createdDate = createdDate;
        this.name = name;
        this.purchasePriceSum = purchasePriceSum;
        this.pointSum = pointSum;
    }

    @QueryProjection
    public UserDto(Long userNo, String username, String name,
                   LocalDateTime createdDate,
                   String email, String phoneNumber,
                   String zoneCode, String roadAddress, String address, String detailAddress,
                   Integer purchasePriceSum, Integer pointSum
    ) {
        this.userNo = userNo;
        this.username = username;
        this.createdDate = createdDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.zoneCode = zoneCode;
        this.roadAddress = roadAddress;
        this.address = address;
        this.detailAddress = detailAddress;
        this.name = name;
        this.purchasePriceSum = purchasePriceSum;
        this.pointSum = pointSum;
    }
}
