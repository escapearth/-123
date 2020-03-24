package org.open.market.model.dto;

import lombok.*;
import org.open.market.model.orders.Order;
import org.open.market.model.orders.OrderItem;
import org.open.market.model.orders.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDto {

    private DeliveryDto deliveryDto;

    private List<OrderItemDto> orderItemDtoList;

    public Order toEntity() {
        List<OrderItem> tempOrderItems = new ArrayList<>();
        orderItemDtoList.forEach((dto) -> tempOrderItems.add(dto.toEntity()));

        return Order.builder()
                .delivery(this.deliveryDto.toEntity())
                .orderItems(tempOrderItems)
                .status(OrderStatus.ORDER)
                .orderAt(LocalDateTime.now())
                .build();
    }
}
