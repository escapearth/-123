package org.open.market.service;

import lombok.RequiredArgsConstructor;
import org.open.market.model.accounts.Account;
import org.open.market.model.accounts.AccountMapper;
import org.open.market.model.delivery.DeliveryStatus;
import org.open.market.model.dto.AccountDto;
import org.open.market.model.dto.OrderDto;
import org.open.market.model.dto.PaymentDto;
import org.open.market.model.items.Item;
import org.open.market.model.orders.Order;
import org.open.market.model.orders.OrderItem;
import org.open.market.model.orders.OrderStatus;
import org.open.market.model.payment.Payment;
import org.open.market.model.payment.PaymentMapper;
import org.open.market.model.payment.PaymentStatus;
import org.open.market.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;

    @Transactional
    public Order createOrder(OrderDto orderDto, AccountDto accountDto) {
        Order order = orderDto.toEntity();

        order.setAccount(AccountMapper.dtoToEntity(accountDto));
//         order.getAccount().addToOrder(order);
        order.getDelivery().setOrder(order);
        List<OrderItem> orderItems = order.getOrderItems();

        int totalPrice = 0;
        for (OrderItem item : orderItems) {
            item.setOrder(order);
            totalPrice += item.getOrderPrice() * item.getQuantity();
        }
        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Order deleteDeliveryStatus(Long orderId, DeliveryStatus deliveryStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        order.getDelivery().setStatus(deliveryStatus);

        return order;
    }

    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        DeliveryStatus deliveryStatus = order.getDelivery().getStatus();
        if (Objects.equals(deliveryStatus, DeliveryStatus.DELIVERY) ||
                Objects.equals(deliveryStatus, DeliveryStatus.DELIVERYOK)) {
            throw new IllegalStateException("해당 상품은 취소가 불가능합니다.");
        }
        order.setStatus(OrderStatus.CANCEL);

        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.forEach(orderItem ->
                itemService.incrementStock(orderItem.getItem().getId(), orderItem.getQuantity()));

        return order;
    }

    @Transactional
    public PaymentDto completePayment(PaymentService paymentService, PaymentDto paymentDto, Long orderId) {
        Order order = getOrder(orderId);
        Payment payment = order.getPayment();
        List<OrderItem> orderItems = order.getOrderItems();

        payment.setPaymentStatus(PaymentStatus.OK);
        payment.setPaymentType(paymentDto.getPaymentType());
        payment.setCreatedDate(LocalDateTime.now());
        payment.setPrice(paymentDto.getPrice());

        paymentDto.setSuperTypePaymentDto(PaymentMapper.toDto(payment));

        orderItems.forEach((orderItem) ->
                itemService.decrementStock(orderItem.getItem().getId(), orderItem.getQuantity())
        );
        return paymentService.savePaymentInfo(paymentDto);
    }
}
