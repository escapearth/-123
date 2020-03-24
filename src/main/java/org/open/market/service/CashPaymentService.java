package org.open.market.service;

import lombok.RequiredArgsConstructor;
import org.open.market.model.dto.CashPaymentDto;
import org.open.market.model.dto.PaymentDto;
import org.open.market.model.payment.CashPayment;
import org.open.market.model.payment.PaymentMapper;
import org.open.market.repository.CashPaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CashPaymentService implements PaymentService {

    private final CashPaymentRepository cashPaymentRepository;

    @Override
    @Transactional
    public PaymentDto savePaymentInfo(PaymentDto paymentDto) {
        CashPaymentDto cashPaymentDto = (CashPaymentDto) paymentDto;
        CashPayment cashPayment = PaymentMapper.toEntity(cashPaymentDto);
        cashPaymentRepository.save(cashPayment);

        return PaymentMapper.toDto(cashPayment);

    }
}
