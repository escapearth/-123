package org.open.market.web;

import lombok.RequiredArgsConstructor;
import org.open.market.common.CurrentUser;
import org.open.market.model.accounts.Account;
import org.open.market.model.dto.OrderDto;
import org.open.market.model.orders.Order;
import org.open.market.service.OrderService;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequiredArgsConstructor
public class BuyController {

    private final OrderService orderService;

    @PostMapping("/api/orders/buyer")
    public ResponseEntity createOrder(@RequestBody OrderDto orderDto,
                                      @CurrentUser Account account) {
        Order order = orderService.createOrder(orderDto, account);
        ControllerLinkBuilder linkBuilder = linkTo(BuyController.class).slash(order.getId());
        URI uri = linkBuilder.toUri();
        Resource<Order> orderResource = new Resource<>(order);
        orderResource.add(linkBuilder.withSelfRel());
        orderResource.add(linkBuilder.withRel("Cancel"));

        return ResponseEntity.created(uri).body(order);
    }
}
