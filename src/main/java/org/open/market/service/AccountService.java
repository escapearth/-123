package org.open.market.service;

import lombok.RequiredArgsConstructor;
import org.open.market.model.accounts.Account;
import org.open.market.model.accounts.AccountMapper;
import org.open.market.model.accounts.AccountSecurityUser;
import org.open.market.model.dto.AccountDto;
import org.open.market.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountDto saveAccount(AccountDto dto) {
        Account account = AccountMapper.dtoToEntity(dto);
        account.setPassword(passwordEncoder.encode(dto.getPassword()));

        return AccountMapper.entityToDto(accountRepository.save(account));
    }

    public AccountDto getAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return AccountMapper.entityToDto(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username).orElseThrow(()
                -> new UsernameNotFoundException(username));

        return AccountSecurityUser.from(AccountMapper.entityToDto(account));
    }
}
