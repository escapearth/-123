package org.open.market.web;

import lombok.RequiredArgsConstructor;
import org.open.market.common.CurrentUser;
import org.open.market.model.cart.CartItem;
import org.open.market.model.dto.AccountDto;
import org.open.market.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PutMapping("/api/carts/items/new")
    public ResponseEntity addItem(@CurrentUser AccountDto accountDto, @RequestBody CartItem cartItem) {
        return ResponseEntity.ok(cartService.addItem(String.valueOf(accountDto.getEmail()), cartItem));
    }

    @GetMapping
    public ResponseEntity getCart(@CurrentUser AccountDto accountDto) {
        return ResponseEntity.ok(cartService.getCart(String.valueOf(accountDto.getEmail())));
    }

    @PutMapping("/items")
    public ResponseEntity removeItem(@CurrentUser AccountDto accountDto, @RequestBody CartItem cartItem) {
        return ResponseEntity.ok(cartService.removeItem(String.valueOf(accountDto.getEmail()), cartItem));
    }

    @DeleteMapping
    public ResponseEntity deleteCart(@CurrentUser AccountDto accountDto) {
        cartService.deleteCart(String.valueOf(accountDto.getId()));
        return ResponseEntity.ok().build();
    }

}
