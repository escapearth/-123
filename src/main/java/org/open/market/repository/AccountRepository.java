package org.open.market.repository;

import org.open.market.model.accounts.Account;
import org.open.market.model.accounts.AccountStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
    Page<Account> findAccountsByStatus(AccountStatus accountStatus, Pageable pageable);
}
