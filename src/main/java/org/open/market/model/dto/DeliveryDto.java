package org.open.market.model.dto;

import lombok.*;
import org.open.market.model.Address;
import org.open.market.model.delivery.Delivery;
import org.open.market.model.delivery.DeliveryStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class DeliveryDto {

    private Address address;

    public Delivery toEntity() {
        return Delivery.builder()
                .address(this.address)
                .status(DeliveryStatus.READY)
                .build();
    }
}
