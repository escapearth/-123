package org.open.market.model.cart;

import lombok.*;
import org.open.market.model.items.Item;

@Getter
@EqualsAndHashCode
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CartItem {
    private Item item;
    private int price;
    private int quantity;

}
