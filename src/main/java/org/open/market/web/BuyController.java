package org.open.market.web;

import lombok.RequiredArgsConstructor;
import org.open.market.common.CurrentUser;
import org.open.market.model.accounts.Account;
import org.open.market.model.dto.AccountDto;
import org.open.market.model.dto.OrderDto;
import org.open.market.model.orders.Order;
import org.open.market.service.OrderService;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequiredArgsConstructor
public class BuyController {

    private final OrderService orderService;

    @PostMapping("/api/orders/buyer")
    public ResponseEntity createOrder(@RequestBody OrderDto orderDto,
                                      @CurrentUser AccountDto  accountDto) {
        if (Objects.isNull(accountDto)) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Order order = orderService.createOrder(orderDto, accountDto);
        ControllerLinkBuilder selfLinkBuilder = linkTo(BuyController.class).slash(order.getId());
        URI uri = selfLinkBuilder.toUri();
        Resource<Order> orderResource = new Resource<>(order);
        orderResource.add(selfLinkBuilder.withSelfRel());
        orderResource.add(selfLinkBuilder.withRel("cancel-order"));

        return ResponseEntity.created(uri).body(orderResource);
    }

    @GetMapping("/api/orders/buyer/{orderId}")
    public ResponseEntity getOrder(@PathVariable("orderId") Long orderId) {
        Order order = orderService.getOrder(orderId);

        Resource<Order> orderResource = new Resource<>(order);
        orderResource.add(linkTo(BuyController.class).slash(order.getId()).withSelfRel());
        orderResource.add(linkTo(BuyController.class).slash(order.getId()).withRel("Cancel"));

        return ResponseEntity.ok(orderResource);
    }

    @DeleteMapping("/api/orders/buyer/{orderId}")
    public ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId,
                                      @CurrentUser AccountDto accountDto) {
        Order order = orderService.getOrder(orderId);

        if (!Objects.equals(order.getAccount().getId(), accountDto.getId())) {
            return new ResponseEntity((HttpStatus.UNAUTHORIZED));
        }

        order = orderService.cancelOrder(orderId);

        Resource<Order> orderResource = new Resource<>(order);
        orderResource.add(linkTo(BuyController.class).withRel("Create"));

        return ResponseEntity.ok(orderResource);
    }
}
