package org.open.market.service;

import lombok.RequiredArgsConstructor;
import org.open.market.model.dto.CreditPaymentDto;
import org.open.market.model.dto.PaymentDto;
import org.open.market.model.payment.CreditPayment;
import org.open.market.model.payment.PaymentMapper;
import org.open.market.repository.CreditPaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CreditPaymentService implements PaymentService {

    private final CreditPaymentRepository creditPaymentRepository;

    @Override
    @Transactional
    public PaymentDto savePaymentInfo(PaymentDto paymentDto) {
        CreditPaymentDto creditPaymentDto = (CreditPaymentDto) paymentDto;
        CreditPayment creditPayment = PaymentMapper.toEntity(creditPaymentDto);
        creditPaymentRepository.save(creditPayment);

        return PaymentMapper.toDto(creditPayment);
    }
}
