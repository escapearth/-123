package org.open.market.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.open.market.model.accounts.Account;
import org.open.market.model.orders.Order;
import org.open.market.model.orders.OrderItem;
import org.open.market.model.orders.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {

    private List<OrderItemDto> orderItemDtoList;

    private Account account;

    public Order to() {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDto orderItemDto : orderItemDtoList) {
            orderItems.add(orderItemDto.to());
        }

        return Order.builder()
                .orderItems(orderItems)
                .account(this.account)
                .status(OrderStatus.ORDER)
                .orderAt(LocalDateTime.now())
                .build();
    }
}
