package com.hellostore.ecommerce.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class BankAccountDto {

    private Long bankAccountId;

    private String bankName;
    private String accountNumber;
    private String accountHolder;
    private String showYn;

    @QueryProjection
    public BankAccountDto(Long bankAccountId, String bankName, String accountNumber, String accountHolder, String showYn) {
        this.bankAccountId = bankAccountId;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.showYn = showYn;
    }
}
