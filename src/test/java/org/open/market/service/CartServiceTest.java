package org.open.market.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.open.market.model.cart.Cart;
import org.open.market.model.cart.CartItem;
import org.open.market.model.items.Item;
import org.open.market.repository.CartRedisRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {

    @InjectMocks
    private CartService cartService;
    @Mock
    private CartRedisRepository cartRedisRepository;

    @Test
    public void add_item() {
        Cart newCart = Cart.of("cartA");
        CartItem cartItem = CartItem.builder().build();
        given(cartRedisRepository.findById("cartA")).willReturn(Optional.of(newCart));

        //when
        cartService.addItem("cartA", cartItem);

        //then
        assertThat(newCart.getCartItemList().get(0)).isEqualTo(cartItem);

    }

    @Test
    public void remove_item_success() {
        //given
        Cart newCart = Cart.of("cartA");
        List<CartItem> list = newCart.getCartItemList();
        Item item = Item.builder()
                .id(1L)
                .stockQuantity(100)
                .price(5000)
                .name("아이템")
                .build();
        CartItem cartItem = CartItem.builder()
                .item(item)
                .price(item.getPrice() * 2)
                .quantity(2)
                .build();
        list.add(cartItem);
        given(cartRedisRepository.findById("cartA")).willReturn(Optional.of(newCart));

        // 상품, 개수, 가격이 일치해야 같은 상품
        CartItem newCartItem = CartItem.builder()
                .item(item)
                .quantity(2)
                .price(10000)
                .build();

        //when
        cartService.removeItem("cartA", newCartItem);

        //then
        assertThat(newCart.getCartItemList().size()).isEqualTo(0);
    }


}
