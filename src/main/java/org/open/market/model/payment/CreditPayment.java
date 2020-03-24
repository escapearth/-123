package org.open.market.model.payment;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CreditPayment {

    @Id
    @GeneratedValue
    @Column(name = "credit_payment_id")
    private Long id;

    private String bank;

    private String cardNumber;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
