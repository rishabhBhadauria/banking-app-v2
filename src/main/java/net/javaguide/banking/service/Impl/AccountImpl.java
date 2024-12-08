package net.javaguide.banking.service.Impl;

import net.javaguide.banking.dto.AccountDto;
import net.javaguide.banking.entity.AccountEntity;
import net.javaguide.banking.mapper.AccountMapper;
import net.javaguide.banking.repository.AccountRepository;
import net.javaguide.banking.service.AccountSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountImpl implements AccountSvc {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        validateAccountDto(accountDto);
        AccountEntity accountEntity = AccountMapper.mapToAccountEntity(accountDto);
        accountEntity = accountRepository.save(accountEntity);
        return AccountMapper.mapToAccountDto(accountEntity);
    }

    @Override
    public AccountDto getAccount(Long id) {
        validateId(id);
        AccountEntity accountEntity = findAccountById(id);
        return AccountMapper.mapToAccountDto(accountEntity);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        validateId(id);
        validateAmount(amount, "Deposit amount must be greater than zero");

        AccountEntity accountEntity = findAccountById(id);
        synchronized (accountEntity) {
            accountEntity.setBalance(accountEntity.getBalance() + amount);
        }

        accountRepository.save(accountEntity);
        return AccountMapper.mapToAccountDto(accountEntity);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        validateId(id);
        validateAmount(amount, "Withdraw amount must be greater than zero");

        AccountEntity accountEntity = findAccountById(id);
        synchronized (accountEntity) {
            if (accountEntity.getBalance() < amount) {
                throw new RuntimeException("Insufficient balance");
            }
            accountEntity.setBalance(accountEntity.getBalance() - amount);
        }

        accountRepository.save(accountEntity);
        return AccountMapper.mapToAccountDto(accountEntity);
    }

    // Utility Methods
    private void validateAccountDto(AccountDto accountDto) {
        if (accountDto == null) {
            throw new IllegalArgumentException("Account details cannot be null");
        }
    }

    private void validateId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Account ID cannot be null");
        }
    }

    private void validateAmount(double amount, String errorMessage) {
        if (amount <= 0) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private AccountEntity findAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account number does not exist"));
    }
}
