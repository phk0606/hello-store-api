package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.BankAccountDto;
import com.hellostore.ecommerce.dto.QBankAccountDto;
import com.hellostore.ecommerce.entity.BankAccount;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hellostore.ecommerce.entity.QBankAccount.bankAccount;

@Repository
public class BankAccountRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public BankAccountRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public BankAccount save(BankAccount bankAccount) {
        em.persist(bankAccount);
        return bankAccount;
    }

    public List<BankAccountDto> getBankAccounts() {
        return queryFactory.select(
                new QBankAccountDto(
                        bankAccount.id,
                        bankAccount.bankName,
                        bankAccount.accountNumber,
                        bankAccount.accountHolder,
                        bankAccount.showYn))
                .from(bankAccount)
                .orderBy(bankAccount.bankName.asc())
                .fetch();
    }

    public void modifyBankAccount(BankAccountDto bankAccountDto) {
        queryFactory.update(bankAccount)
                .set(bankAccount.bankName, bankAccountDto.getBankName())
                .set(bankAccount.accountNumber, bankAccountDto.getAccountNumber())
                .set(bankAccount.accountHolder, bankAccountDto.getAccountHolder())
                .set(bankAccount.showYn, bankAccountDto.getShowYn())
                .where(bankAccount.id.eq(bankAccountDto.getBankAccountId()))
                .execute();
    }

    public void removeBankAccount(BankAccountDto bankAccountDto) {
        queryFactory.delete(bankAccount)
                .where(bankAccount.id.eq(bankAccountDto.getBankAccountId()))
                .execute();
    }

    public BankAccountDto getBankAccount(Long bankAccountId) {
        return queryFactory.select(
                        new QBankAccountDto(
                                bankAccount.id, bankAccount.bankName,
                                bankAccount.accountNumber, bankAccount.accountHolder,
                                bankAccount.showYn))
                .from(bankAccount)
                .where(bankAccount.id.eq(bankAccountId))
                .fetchOne();
    }

    public BankAccount getBankAccountById(Long bankAccountId) {
        return em.find(BankAccount.class, bankAccountId);
    }
}
