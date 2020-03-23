package org.open.market.model.accounts;

import org.open.market.model.dto.AccountDto;

import java.time.LocalDateTime;
import java.util.Objects;

public class AccountMapper {

    public static AccountDto entityToDto(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .nickname(account.getNickname())
                .email(account.getEmail())
                .password(account.getPassword())
                .phone(account.getPhone())
                .accountRole(account.getAccountRole())
                .status(account.getStatus())
                .build();
    }

    public static Account dtoToEntity(AccountDto accountDto) {
        Account account = Account.builder()
                .nickname(accountDto.getNickname())
                .email(accountDto.getEmail())
                .password(accountDto.getPassword())
                .phone(accountDto.getPhone())
                .accountRole(accountDto.getAccountRole())
                .status(AccountStatus.USER)
                .build();

        if (Objects.nonNull(account.getId())) {
            account.setId(accountDto.getId());
            account.setStatus(accountDto.getStatus());
        }

        return account;
    }
}
