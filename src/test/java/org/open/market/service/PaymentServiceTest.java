package org.open.market.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.open.market.model.dto.CashPaymentDto;
import org.open.market.model.dto.PaymentDto;
import org.open.market.model.payment.PaymentStatus;
import org.open.market.model.payment.PaymentType;
import org.open.market.repository.CashPaymentRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTest {

    @InjectMocks
    private CashPaymentService cashPaymentService;

    @Mock
    private CashPaymentRepository cashPaymentRepository;

    @Test
    public void savePaymentInfo_cash() {
        //given
        PaymentDto superPaymentDto = PaymentDto.builder()
                .id(1L)
                .paymentStatus(PaymentStatus.OK)
                .paymentType(PaymentType.CASH)
                .price(10000)
                .build();
        CashPaymentDto cashPaymentDto = CashPaymentDto.builder()
                .cashPaymentId(2L)
                .name("tester")
                .bank("kakao")
                .bankAccount("1234")
                .superTypePaymentDto(superPaymentDto)
                .build();

        //when
        PaymentDto returnDto = cashPaymentService.savePaymentInfo(cashPaymentDto);
        CashPaymentDto returnCashPaymentDto = (CashPaymentDto)returnDto;

        assertThat(returnCashPaymentDto.getCashPaymentId()).isEqualTo(2L);
        assertThat(returnCashPaymentDto.getName()).isEqualTo("tester");
        assertThat(returnCashPaymentDto.getBank()).isEqualTo("kakao");
        assertThat(returnCashPaymentDto.getBankAccount()).isEqualTo("1234");
    }
}
