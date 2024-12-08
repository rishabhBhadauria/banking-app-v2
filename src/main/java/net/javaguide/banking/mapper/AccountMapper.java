package net.javaguide.banking.mapper;

import net.javaguide.banking.dto.AccountDto;
import net.javaguide.banking.entity.AccountEntity;

import java.util.Optional;

public class AccountMapper {
    public static AccountEntity mapToAccountEntity(AccountDto accountDto) {
        return new AccountEntity(accountDto.getId(),accountDto.getHolderName(),accountDto.getBalance());
    }

    public static AccountDto mapToAccountDto(AccountEntity accountEntity) {
        return new AccountDto(accountEntity.getId(), accountEntity.getHolderName(),accountEntity.getBalance());
    }

}
