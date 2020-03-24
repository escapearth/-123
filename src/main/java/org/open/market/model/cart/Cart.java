package org.open.market.model.cart;

import lombok.*;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Cart {

    @Id
    private String id;

    private List<CartItem> cartItemList;

    private Cart(String id) {
        this.id = id;
        this.cartItemList = new ArrayList<>();
    }

    public static Cart of(String id) {
        return new Cart(id);
    }
}
