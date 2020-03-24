package org.open.market.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.open.market.model.payment.PaymentStatus;
import org.open.market.model.payment.PaymentType;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
public class PaymentDto {

    private Long id;

    private int price;

    private PaymentStatus paymentStatus;

    private PaymentType paymentType;

    private PaymentDto superTypePaymentDto;
}
