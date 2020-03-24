package org.open.market.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.open.market.common.CommonControllerTest;
import org.open.market.model.Address;
import org.open.market.model.accounts.AccountRole;
import org.open.market.model.cart.Cart;
import org.open.market.model.cart.CartItem;
import org.open.market.model.dto.AccountDto;
import org.open.market.model.items.Item;
import org.open.market.repository.AccountRepository;
import org.open.market.repository.CartRedisRepository;
import org.open.market.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CartControllerTest extends CommonControllerTest {

    @Autowired
    private AccountService accountService;
    @Autowired
    private CartRedisRepository cartRedisRepository;
    @Autowired
    private AccountRepository accountRepository;

    Item item1 = Item.builder()
            .id(1L)
            .name("아이템1")
            .price(10000)
            .stockQuantity(100)
            .build();
    Item item2 = Item.builder()
            .id(2L)
            .name("아이템2")
            .price(20000)
            .stockQuantity(200)
            .build();

    CartItem cartItem1 = CartItem.builder()
            .item(item1)
            .price(item1.getPrice() * 2)
            .quantity(2)
            .build();
    CartItem cartItem2 = CartItem.builder()
            .item(item2)
            .price(item2.getPrice() * 3)
            .quantity(3)
            .build();

    @Before
    public void generateAccount() {
        AccountDto accountDto = AccountDto.builder()
                .accountRole(Collections.unmodifiableSet(new HashSet<>(Arrays.asList(AccountRole.USER))))
                .address(new Address("a", "b", "c"))
                .email("test@naver.com")
                .nickname("tester")
                .phone("01012341234")
                .password("1234")
                .build();
        accountService.saveAccount(accountDto);
    }

    @After
    public void tearDown() {
        cartRedisRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void createCart() throws Exception {

        mockMvc.perform(post("/api/carts")
//                .header(HttpHeaders.AUTHORIZATION, getAccessToken()))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("cartItemList").exists());
    }

    @Test
    public void getCart() throws Exception {

        //given
        Cart cart = Cart.builder()
                .id("tester@naver.com")
                .cartItemList(Arrays.asList(cartItem1, cartItem2))
                .build();
        cartRedisRepository.save(cart);

        //when & then
        mockMvc.perform(get("/api/carts")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                 .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("cartItemList").exists())
                .andExpect(jsonPath("cartItemList[0].item").exists());
    }



}
