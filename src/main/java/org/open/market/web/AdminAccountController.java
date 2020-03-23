package org.open.market.web;

import lombok.RequiredArgsConstructor;
import org.open.market.model.accounts.Account;
import org.open.market.model.accounts.AccountStatus;
import org.open.market.model.dto.AccountDto;
import org.open.market.service.AccountService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

/**
* @author halfdev
* @since 2020-03-23
* Admin 전용 Page
*/
@Controller
@RequestMapping("/admin/accounts")
@RequiredArgsConstructor
public class AdminAccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity getAccountWithStatus(@RequestParam AccountStatus accountStatus, Pageable pageable,
        PagedResourcesAssembler<Account> pagedResourcesAssembler) {
        PagedResources pagedResources = accountService.findAllAccountWithStatus(accountStatus, pageable, pagedResourcesAssembler);

        return ResponseEntity.ok(pagedResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity getAccount(@PathVariable("id") Long id) {
        AccountDto accountDto;

        try {
            accountDto = accountService.getAccount(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(accountDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAccount(@PathVariable("id") Long id,
                                        @RequestParam AccountStatus accountStatus) {
        AccountDto accountDto;

        try {
            accountDto = accountService.getAccount(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        if (accountStatus.equals(AccountStatus.DELETED)) {
            return ResponseEntity.badRequest().build(); // ADMIN 이 DELETED 로 불가능
        }
        accountService.deleteAccountStatus(id, accountStatus);

        return ResponseEntity.ok(accountService.getAccount(accountDto.getId()));
    }


}

