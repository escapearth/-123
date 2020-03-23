package org.open.market.web;

import lombok.RequiredArgsConstructor;
import org.open.market.common.CurrentUser;
import org.open.market.model.accounts.AccountStatus;
import org.open.market.model.dto.AccountDto;
import org.open.market.service.AccountService;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping(value = "/api/accounts")
    public ResponseEntity createAccount(@RequestBody @Valid AccountDto accountDtoRequest) {
        AccountDto accountDto = accountService.saveAccount(accountDtoRequest);
        URI uri = ControllerLinkBuilder.linkTo(AccountController.class).slash(accountDto.getId()).toUri();

        return ResponseEntity.created(uri).body(accountDto);
    }

    @GetMapping(value = "/api/accounts")
    public ResponseEntity getAccount(@CurrentUser AccountDto accountDto) {
        return ResponseEntity.ok(accountService.getAccount(accountDto.getId()));
    }

//    @GetMapping(value = "/api/accounts")
//    public List<Account> getAllAccounts() {
//        return this.accountService.getAllAccounts();
//    }

    @PutMapping(value = "/api/accounts")
    public ResponseEntity updateAccount(@RequestBody @Valid AccountDto accountDto,
                                        @CurrentUser AccountDto currentUser) {
        AccountDto updateAccount = accountService.updateAccount(currentUser.getId(), accountDto);

        return ResponseEntity.ok(updateAccount);
    }

    @DeleteMapping(value = "/api/accounts")
    public ResponseEntity deleteAccount(@CurrentUser AccountDto accountDto) {
        accountService.deleteAccountStatus(accountDto.getId(), AccountStatus.DELETED);

        return ResponseEntity.ok().build();
    }

}
