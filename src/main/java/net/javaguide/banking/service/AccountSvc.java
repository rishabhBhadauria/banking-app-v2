package net.javaguide.banking.service;

import net.javaguide.banking.dto.AccountDto;
import net.javaguide.banking.entity.AccountEntity;

public interface AccountSvc {
    AccountDto createAccount(AccountDto accountDto);
    AccountDto getAccount(Long id);
    AccountDto deposit(Long id, double amount);
    AccountDto withdraw(Long id, double amount);

}
