package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.BankAccountDto;
import com.hellostore.ecommerce.entity.BankAccount;
import com.hellostore.ecommerce.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    @Transactional
    public void createBankAccount(BankAccountDto bankAccountDto) {

        BankAccount bankAccount = BankAccount.builder()
                .accountNumber(bankAccountDto.getAccountNumber())
                .accountHolder(bankAccountDto.getAccountHolder())
                .bankName(bankAccountDto.getBankName())
                .showYn(bankAccountDto.getShowYn())
                .build();

        bankAccountRepository.save(bankAccount);
    }

    public List<BankAccountDto> getBankAccounts() {
        return bankAccountRepository.getBankAccounts();
    }

    @Transactional
    public void modifyBankAccount(BankAccountDto bankAccountDto) {
        bankAccountRepository.modifyBankAccount(bankAccountDto);
    }

    @Transactional
    public void removeBankAccount(BankAccountDto bankAccountDto) {
        bankAccountRepository.removeBankAccount(bankAccountDto);
    }

    public BankAccountDto getBankAccount(Long bankAccountId) {
        return bankAccountRepository.getBankAccount(bankAccountId);
    }
}
