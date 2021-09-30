package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_account_id")
    private Long id;

    private String bankName;
    private String accountNumber;
    private String accountHolder;
    private String showYn;

    @OneToOne(mappedBy = "bankAccount", fetch = FetchType.LAZY)
    private Order order;

    @Builder
    public BankAccount(String bankName, String accountNumber, String accountHolder, String showYn) {
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.showYn = showYn;
    }
}
