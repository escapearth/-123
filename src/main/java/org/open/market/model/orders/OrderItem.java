package org.open.market.model.orders;

import lombok.*;
import org.open.market.model.items.Item;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int quantity;

    @Builder
    public OrderItem(Item item, Order order, int orderPrice, int quantity) {
        this.item = item;
        this.order = order;
        this.orderPrice = orderPrice;
        this.quantity = quantity;
    }
}
