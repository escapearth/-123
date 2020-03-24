package org.open.market.model.payment;

import org.open.market.model.dto.CashPaymentDto;
import org.open.market.model.dto.CreditPaymentDto;
import org.open.market.model.dto.PaymentDto;

public class PaymentMapper {

    public static Payment toEntity(PaymentDto paymentDto) {
        return Payment.builder()
                .id(paymentDto.getId())
                .price(paymentDto.getPrice())
                .paymentType(paymentDto.getPaymentType())
                .paymentStatus(paymentDto.getPaymentStatus())
                .build();
    }

    public static CashPayment toEntity(CashPaymentDto cashPaymentDto) {
        return CashPayment.builder()
                .id(cashPaymentDto.getCashPaymentId())
                .bank(cashPaymentDto.getBank())
                .bankAccount(cashPaymentDto.getBankAccount())
                .name(cashPaymentDto.getName())
                .payment(toEntity(cashPaymentDto.getSuperTypePaymentDto()))
                .build();
    }

    public static CreditPayment toEntity(CreditPaymentDto creditPaymentDto) {
        return CreditPayment.builder()
                .id(creditPaymentDto.getCreditPaymentId())
                .bank(creditPaymentDto.getBank())
                .cardNumber(creditPaymentDto.getCardNumber())
                .name(creditPaymentDto.getName())
                .payment(toEntity(creditPaymentDto.getSuperTypePaymentDto()))
                .build();
    }

    public static PaymentDto toDto(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .price(payment.getPrice())
                .paymentStatus(payment.getPaymentStatus())
                .paymentType(payment.getPaymentType())
                .build();
    }

    public static PaymentDto toDto(CashPayment cashPayment) {
        return CashPaymentDto.builder()
                .cashPaymentId(cashPayment.getId())
                .bank(cashPayment.getBank())
                .bankAccount(cashPayment.getBankAccount())
                .name(cashPayment.getName())
                .build();
    }

    public static PaymentDto toDto(CreditPayment creditPayment) {
        return CreditPaymentDto.builder()
                .creditPaymentId(creditPayment.getId())
                .bank(creditPayment.getBank())
                .cardNumber(creditPayment.getCardNumber())
                .name(creditPayment.getName())
                .build();
    }
}
