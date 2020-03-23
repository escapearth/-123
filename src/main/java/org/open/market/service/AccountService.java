package org.open.market.service;

import lombok.RequiredArgsConstructor;
import org.open.market.model.accounts.Account;
import org.open.market.model.accounts.AccountMapper;
import org.open.market.model.accounts.AccountSecurityUser;
import org.open.market.model.accounts.AccountStatus;
import org.open.market.model.dto.AccountDto;
import org.open.market.repository.AccountRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AccountDto saveAccount(AccountDto dto) {
        Account account = AccountMapper.dtoToEntity(dto);
        account.setPassword(passwordEncoder.encode(dto.getPassword()));

        return AccountMapper.entityToDto(accountRepository.save(account));
    }

    public AccountDto getAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return AccountMapper.entityToDto(account);
    }

    public PagedResources findAllAccountWithStatus(AccountStatus accountStatus, Pageable pageable,
                                                   PagedResourcesAssembler<Account> pagedResourcesAssembler) {
        return pagedResourcesAssembler.toResource(accountRepository.findAccountsByStatus(accountStatus, pageable));
    }

    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() throws DataAccessException {
        return accountRepository.findAll();
    }

    @Transactional
    public AccountDto updateAccount(Long id, AccountDto accountDto) {
        Account account = accountRepository.findById(id).get();
        account.update(accountDto);

        return AccountMapper.entityToDto(account);

    }

    @Transactional
    public void deleteAccountStatus(Long id, AccountStatus deleted) {
        Account account = accountRepository.findById(id).get();
        account.setStatus(deleted);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username).orElseThrow(()
                -> new UsernameNotFoundException(username));

        return AccountSecurityUser.from(AccountMapper.entityToDto(account));
    }
}
