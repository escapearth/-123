package org.open.market.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.open.market.common.CommonControllerTest;
import org.open.market.model.accounts.Account;
import org.open.market.model.accounts.AccountRole;
import org.open.market.model.dto.AccountDto;
import org.open.market.repository.AccountRepository;
import org.open.market.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest extends CommonControllerTest {

    @Autowired private AccountRepository accountRepository;
    @Autowired private AccountService accountService;

    @Before
    public void tearDown() {
        this.accountRepository.deleteAll();
    }

    private AccountDto generateAccount(int index) {
        AccountDto accountDto = AccountDto.builder()
                .accountRole(Collections.unmodifiableSet(new HashSet<>(Arrays.asList(AccountRole.USER))))
                .email("defaultEmail" + index + "@naver.com")
                .nickname("defaultNickname" + index)
                .phone("123451235")
                .password("1234")
                .build();

        return accountService.saveAccount(accountDto);
    }

    @Test
    public void createAccountSuccess() throws Exception {
        AccountDto accountDto = AccountDto.builder()
                .email("11111@naver.com")
                .password("1234")
                .nickname("n11111")
                .phone("010-1234-1234")
                .accountRole(Collections.unmodifiableSet(new HashSet<>(Arrays.asList(AccountRole.USER))))
                .build();

        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(accountDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("nickname").value("n11111"));
    }

    @Test
    public void createAccount_Wrong_Input() throws Exception {
        AccountDto dto = AccountDto.builder().build();

        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAccount() throws Exception {

        AccountDto accountDto = generateAccount(1);

        this.mockMvc.perform(get("/api/accounts")
                .header(HttpHeaders.AUTHORIZATION, accountDto)) // Exception
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("nickname").exists())
                .andExpect(jsonPath("phone").exists())
                .andExpect(jsonPath("status").value("USER"));
    }

}
