package org.open.market.repository;

import org.open.market.model.payment.CreditPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditPaymentRepository extends JpaRepository<CreditPayment, Long> {
}
