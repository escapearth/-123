package org.open.market.web;

import lombok.RequiredArgsConstructor;
import org.open.market.model.dto.AccountDto;
import org.open.market.service.AccountService;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
