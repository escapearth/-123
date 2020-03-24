package org.open.market.web;

import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.open.market.common.CommonControllerTest;
import org.open.market.model.Address;
import org.open.market.model.accounts.AccountMapper;
import org.open.market.model.accounts.AccountRole;
import org.open.market.model.dto.*;
import org.open.market.model.items.Item;
import org.open.market.service.AccountService;
import org.open.market.service.ItemService;
import org.open.market.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BuyControllerTest extends CommonControllerTest {

    @Autowired
    private AccountService accountService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderService orderService;

    @Test
    @Transactional
    public void createOrderSuccess() throws Exception {
        // SELLER
        AccountDto sellerAccountDto = AccountDto.builder()
                .accountRole(Collections.unmodifiableSet(new HashSet<>(Arrays.asList(AccountRole.USER,AccountRole.SELLER))))
                .address(new Address("a", "b", "c"))
                .email("seller@naver.com")
                .nickname("seller")
                .phone("01012341234")
                .password("1234")
                .build();
        AccountDto sellerDto = accountService.saveAccount(sellerAccountDto);

        // Product
        ItemDto itemDto1 = ItemDto.builder()
                .account(AccountMapper.dtoToEntity(sellerDto))
                .name("ProductA")
                .price(10000)
                .stockQuantity(100)
                .build();
        Item item1 = itemService.saveItem(itemDto1);

        ItemDto itemDto2 = ItemDto.builder()
                .account(AccountMapper.dtoToEntity(sellerDto))
                .name("ProductA")
                .price(20000)
                .stockQuantity(200)
                .build();
        Item item2 = itemService.saveItem(itemDto2);

        // Delivery Info
        DeliveryDto deliveryDto = DeliveryDto.builder()
                .address(new Address("a", "b", "c"))
                .build();

        // Order Product Info
        OrderItemDto orderItemDto1 = OrderItemDto.builder()
                .itemDto(modelMapper.map(item1,ItemDto.class))
                .quantity(2)
                .orderPrice(itemDto1.getPrice()*2)
                .build();
        OrderItemDto orderItemDto2 = OrderItemDto.builder()
                .itemDto(modelMapper.map(item2,ItemDto.class))
                .quantity(3)
                .orderPrice(itemDto2.getPrice()*3)
                .build();

        // Buyer Info
        AccountDto buyerAccountDto = AccountDto.builder()
                .accountRole(Collections.unmodifiableSet(new HashSet<>(Arrays.asList(AccountRole.USER))))
                .address(new Address("a", "b", "c"))
                .email("buyer@naver.com")
                .nickname("buyer")
                .phone("01012341234")
                .password("12345")
                .build();
        AccountDto buyerDto = accountService.saveAccount(buyerAccountDto);

        // Order Info
        OrderDto orderDto = OrderDto.builder()
                .deliveryDto(deliveryDto)
                .orderItemDtoList(Arrays.asList(orderItemDto1, orderItemDto2))
                .build();

        mockMvc.perform(post("/api/orders/buyer")
                .header(HttpHeaders.AUTHORIZATION, buyerAccountDto)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsBytes(orderDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

}
