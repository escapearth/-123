package org.open.market.web;

import lombok.RequiredArgsConstructor;
import org.open.market.model.accounts.Account;
import org.open.market.model.accounts.AccountStatus;
import org.open.market.service.AccountService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

}

