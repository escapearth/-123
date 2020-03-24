package org.open.market.model.payment;

import org.open.market.service.CashPaymentService;
import org.open.market.service.CreditPaymentService;
import org.open.market.service.PaymentService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PaymentFactory {

    private final Map<PaymentType, PaymentService> map;

    public PaymentFactory(CashPaymentService cashPaymentService,
                          CreditPaymentService creditPaymentService) {
        map = new HashMap<>();
        map.put(PaymentType.CASH, cashPaymentService);
        map.put(PaymentType.CREDIT, creditPaymentService);
    }

    public PaymentService getType(PaymentType paymentType) {
        if(map.containsKey(paymentType)) {
            return map.get(paymentType);
        } else {
            throw new IllegalArgumentException();
        }
    }

}
