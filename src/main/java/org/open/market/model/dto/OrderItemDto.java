package org.open.market.model.dto;

import lombok.Builder;
import lombok.Getter;
import org.open.market.model.orders.OrderItem;

@Getter
@Builder
public class OrderItemDto {

    private int orderPrice;
    private int quantity;
    private ItemDto itemDto;

    public OrderItem to() {
        return OrderItem.builder()
                .orderPrice(this.orderPrice)
                .quantity(this.quantity)
                .item(this.itemDto.from())
                .build();
    }
}
