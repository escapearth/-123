package org.open.market.model.payment;

import lombok.*;
import org.open.market.model.payment.Payment;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CashPayment {

    @Id
    @GeneratedValue
    @Column(name = "cash_payment_id")
    private Long id;

    private String bank;

    private String bankAccount;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
