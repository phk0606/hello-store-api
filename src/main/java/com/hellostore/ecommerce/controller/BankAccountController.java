package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.BankAccountDto;
import com.hellostore.ecommerce.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bankAccount")
@RequiredArgsConstructor
@Slf4j
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @PostMapping("/createBankAccount")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void createBankAccount(@RequestBody BankAccountDto bankAccountDto) {
        log.debug("bankAccountDto: {}", bankAccountDto);
        bankAccountService.createBankAccount(bankAccountDto);
    }

    @PutMapping("/modifyBankAccount")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void modifyBankAccount(@RequestBody BankAccountDto bankAccountDto) {
        bankAccountService.modifyBankAccount(bankAccountDto);
    }

    @DeleteMapping("/removeBankAccount")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void removeBankAccount(@RequestBody BankAccountDto bankAccountDto) {
        bankAccountService.removeBankAccount(bankAccountDto);
    }

    @GetMapping("/getBankAccounts")
    public List<BankAccountDto> getBankAccounts() {
        return bankAccountService.getBankAccounts();
    }

    @GetMapping("/getBankAccount")
    public BankAccountDto getBankAccount(@RequestParam Long bankAccountId) {
        return bankAccountService.getBankAccount(bankAccountId);
    }
}
