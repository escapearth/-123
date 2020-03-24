package org.open.market.web;

import lombok.RequiredArgsConstructor;
import org.open.market.model.delivery.DeliveryStatus;
import org.open.market.model.orders.Order;
import org.open.market.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SellerController {

    private final OrderService orderService;

    @PatchMapping("/api/orders/seller/{orderId}/delivery")
    public ResponseEntity changeDeliveryStatus(@PathVariable Long orderId,
                                               @RequestParam DeliveryStatus deliveryStatus) {
        Order order = orderService.deleteDeliveryStatus(orderId, deliveryStatus);

        return ResponseEntity.ok(order);
    }
}
