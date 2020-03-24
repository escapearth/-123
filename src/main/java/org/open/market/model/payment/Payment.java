package org.open.market.model.payment;

import lombok.*;
import org.open.market.common.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "payment_id")
    private Long id;

    private int price;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    public static Payment toReadyEntity() {
        return Payment.builder()
                .paymentStatus(PaymentStatus.READY)
                .build();
    }
}
