package org.open.market.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // for reflection, proxy
@Getter
public class Address {
    private String city;
    private String street;
    private String zipCode;
}
