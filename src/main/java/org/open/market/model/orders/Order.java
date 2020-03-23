package org.open.market.model.orders;

import lombok.*;
import org.open.market.model.accounts.Account;
import org.open.market.model.delivery.Delivery;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders", indexes = {@Index(columnList = "orderAt")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderAt;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Builder
    public Order(Account account, List<OrderItem> orderItems, LocalDateTime orderAt, OrderStatus status, Delivery delivery) {
        this.account = account;
        this.orderItems = orderItems;
        this.orderAt = orderAt;
        this.status = status;
        this.delivery = delivery;
    }
}
