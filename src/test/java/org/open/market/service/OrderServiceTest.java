package org.open.market.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.open.market.model.Address;
import org.open.market.model.accounts.Account;
import org.open.market.model.accounts.AccountRole;
import org.open.market.model.delivery.Delivery;
import org.open.market.model.delivery.DeliveryStatus;
import org.open.market.model.dto.AccountDto;
import org.open.market.model.dto.OrderDto;
import org.open.market.model.dto.PaymentDto;
import org.open.market.model.items.Item;
import org.open.market.model.orders.Order;
import org.open.market.model.orders.OrderItem;
import org.open.market.model.orders.OrderStatus;
import org.open.market.model.payment.Payment;
import org.open.market.model.payment.PaymentStatus;
import org.open.market.model.payment.PaymentType;
import org.open.market.repository.OrderRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderDto orderDto;

    @Mock
    ItemService itemService;

    @Mock
    PaymentService paymentService;


    private Account seller = Account.builder()
            .accountRole(Collections.unmodifiableSet(new HashSet<>(Arrays.asList(AccountRole.USER))))
            .address(new Address("a", "b", "c"))
            .email("seller@naver.com")
            .nickname("seller")
            .phone("01012341234")
            .password("pass")
            .build();

    private AccountDto buyerDto = AccountDto.builder()
            .accountRole(Collections.unmodifiableSet(new HashSet<>(Arrays.asList(AccountRole.USER))))
            .address(new Address("a", "b", "c"))
            .email("buyer@naver.com")
            .nickname("buyer")
            .phone("01012341234")
            .password("pass")
            .build();

    private Item item1 = Item.builder()
            .id(1L)
            .account(seller)
            .name("상품1")
            .price(10000)
            .stockQuantity(100)
            .build();

    private Item item2 = Item.builder()
            .id(2L)
            .account(seller)
            .name("상품2")
            .price(20000)
            .stockQuantity(200)
            .build();

    private Delivery delivery = Delivery.builder()
            .id(1L)
            .address(new Address("a", "b", "c"))
            .build();

    private OrderItem orderItem1 = OrderItem.builder()
            .id(1L)
            .item(item1)
            .quantity(2)
            .orderPrice(item1.getPrice() * 2)
            .build();
    private OrderItem orderItem2 = OrderItem.builder()
            .id(2L)
            .item(item2)
            .quantity(3)
            .orderPrice(item2.getPrice() * 3)
            .build();

    private Payment payment = Payment.builder()
            .id(1L)
            .paymentStatus(PaymentStatus.READY)
            .build();

    private Order order = Order.builder()
            .id(1L)
            .orderAt(LocalDateTime.now())
            .status(OrderStatus.ORDER)
            .orderItems(Arrays.asList(orderItem1, orderItem2))
            .delivery(delivery)
            .payment(payment)
            .build();

    @Test
    public void createOrder() {

        //given
        given(orderDto.toEntity()).willReturn(order);

        //when
        orderService.createOrder(orderDto, buyerDto);

        //then
        verify(orderRepository, times(1)).save(order);
        assertThat(order.getDelivery().getOrder()).isEqualTo(order);
        assertThat(order.getAccount().getEmail()).isEqualTo(buyerDto.getEmail());
        assertThat(order.getOrderItems().get(0).getOrder()).isEqualTo(order);
        assertThat(order.getPayment().getPaymentStatus()).isEqualTo(PaymentStatus.READY);
    }

    @Test
    public void getOrder() {
        //given
        given(orderRepository.findById(1L)).willReturn(Optional.of(order));

        //when
        Order getOrder = orderService.getOrder(1L);

        assertThat(getOrder.getOrderItems()).isEqualTo(order.getOrderItems());
        assertThat(getOrder.getDelivery()).isEqualTo(order.getDelivery());
    }

    @Test(expected = EntityNotFoundException.class)
    public void getOrder_Throw_Entity_Not_Found() {
        //given
        Order order = orderDto.toEntity();
        given(orderRepository.findById(1L)).willReturn(Optional.empty());

        //when
        orderService.getOrder(1L);
    }

    @Test
    public void changeDeliveryStatus() {
        //given
        given(orderRepository.findById(1L)).willReturn(Optional.of(order));

        //when
        orderService.deleteDeliveryStatus(1L, DeliveryStatus.DELIVERY);

        //then
        assertThat(order.getDelivery().getStatus()).isEqualTo(DeliveryStatus.DELIVERY);
    }

    @Test(expected = EntityNotFoundException.class)
    public void changeDeliveryStatus_Throw_Entity_Not_Found() {
        //given
        given(orderRepository.findById(1L)).willReturn(Optional.empty());

        //when
        orderService.deleteDeliveryStatus(1L, DeliveryStatus.DELIVERY);
    }

    @Test
    public void cancelOrder() {
        //given
        given(orderRepository.findById(1L)).willReturn(Optional.of(order));

        //when
        orderService.cancelOrder(1L);

        //then
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCEL);
    }

    @Test(expected = EntityNotFoundException.class)
    public void cancelOrder_Throw_Entity_Not_Found() {
        //given
        given(orderRepository.findById(1L)).willReturn(Optional.empty());

        //when
        orderService.cancelOrder(1L);
    }

    @Test(expected = IllegalStateException.class)
    public void cancelOrder_Throw_Illegal_State() {
        //given
        order.getDelivery().setStatus(DeliveryStatus.DELIVERY);
        given(orderRepository.findById(1L)).willReturn(Optional.of(order));

        //when
        orderService.cancelOrder(1L);
    }

    @Test
    public void completePayment() {

        //given
        PaymentDto paymentDto = PaymentDto.builder()
                .price(10000)
                .paymentType(PaymentType.CASH)
                .build();
        given(orderRepository.findById(1L)).willReturn(Optional.of(order));


        //when
        orderService.completePayment(paymentService, paymentDto, 1L);

        //then
        assertThat(order.getPayment().getPaymentStatus()).isEqualTo(PaymentStatus.OK);
        assertThat(order.getPayment().getPrice()).isEqualTo(10000);
        assertThat(order.getPayment().getPaymentType()).isEqualTo(PaymentType.CASH);
    }
}
