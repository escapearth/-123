package org.open.market.service;

import lombok.RequiredArgsConstructor;
import org.open.market.model.accounts.Account;
import org.open.market.model.dto.OrderDto;
import org.open.market.model.items.Item;
import org.open.market.model.orders.Order;
import org.open.market.model.orders.OrderItem;
import org.open.market.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;

    @Transactional
    public Order createOrder(OrderDto orderDto, Account account) {
        Order order = orderDto.to();

        order.setAccount(account);
        account.addToOrder(order);
        order.getDelivery().setOrder(order);
        List<OrderItem> orderItems = order.getOrderItems();

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
        }

        // 주문 완료 후 처리
        for (OrderItem orderItem : orderItems) {
            Item item = orderItem.getItem();
            itemService.removeStock(item.getId(), orderItem.getQuantity());
        }

        return orderRepository.save(order);
    }
}
