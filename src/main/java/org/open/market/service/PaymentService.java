package org.open.market.service;

import org.open.market.model.dto.PaymentDto;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
    PaymentDto savePaymentInfo(PaymentDto paymentDto);
}
