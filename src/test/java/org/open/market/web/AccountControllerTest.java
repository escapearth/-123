package org.open.market.web;

import org.junit.Before;
import org.junit.Test;
import org.open.market.common.CommonControllerTest;
import org.open.market.model.accounts.AccountRole;
import org.open.market.model.dto.AccountDto;
import org.open.market.repository.AccountRepository;
import org.open.market.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

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

    @Test
    public void createAccountSuccess() throws Exception {
        AccountDto accountDto = AccountDto.builder()
                .email("test@naver.com")
                .password("1234")
                .nickname("nicknameaaa")
                .phone("010-1234-1234")
                .accountRole(Collections.unmodifiableSet(new HashSet<>(Arrays.asList(AccountRole.USER))))
                .build();

        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(accountDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("nickname").value("nicknameaaa"));
    }
}
