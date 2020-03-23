package org.open.market.model.accounts;

import org.open.market.model.dto.AccountDto;

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
                .address(account.getAddress())
                .build();
    }

    public static Account dtoToEntity(AccountDto accountDto) {
        Account account = Account.builder()
                .nickname(accountDto.getNickname())
                .email(accountDto.getEmail())
                .password(accountDto.getPassword())
                .phone(accountDto.getPhone())
                .accountRole(accountDto.getAccountRole())
                .address(accountDto.getAddress())
                .status(AccountStatus.USER)
                .build();

        if (Objects.nonNull(account.getId())) {
            account.setId(accountDto.getId());
            account.setStatus(accountDto.getStatus());
        }

        return account;
    }
}
