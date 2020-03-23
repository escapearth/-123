package org.open.market.model.accounts;

import lombok.Getter;
import org.open.market.model.dto.AccountDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class AccountSecurityUser extends User {

    private final AccountDto dto;

    private AccountSecurityUser(AccountDto dto) {
        super(dto.getEmail(), dto.getPassword(), authorities((dto.getAccountRole())));
        this.dto = dto;
    }

    public static AccountSecurityUser from(AccountDto dto) {
        return new AccountSecurityUser(dto);
    }

    private static Collection<? extends GrantedAuthority> authorities(Set<AccountRole> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet());
    }
}
