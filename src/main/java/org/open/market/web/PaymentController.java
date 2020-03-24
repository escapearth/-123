package org.open.market.web;

import lombok.RequiredArgsConstructor;
import org.open.market.model.dto.PaymentDto;
import org.open.market.model.payment.PaymentFactory;
import org.open.market.service.OrderService;
import org.open.market.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentFactory paymentFactory;
    private final OrderService orderService;

    @PostMapping(value = "/api/orders/payment/{orderId}")
    public ResponseEntity completePayment(@RequestBody PaymentDto paymentDto,
                                          @PathVariable Long orderId) {
        final PaymentService paymentService = paymentFactory.getType(paymentDto.getPaymentType());
        return ResponseEntity.ok(orderService.completePayment(paymentService, paymentDto, orderId));
    }

}
