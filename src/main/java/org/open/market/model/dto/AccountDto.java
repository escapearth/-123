package org.open.market.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import org.open.market.common.BaseTimeEntity;
import org.open.market.model.Address;
import org.open.market.model.accounts.AccountRole;
import org.open.market.model.accounts.AccountStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
* @author halfdev
* @since 2020-03-23
*/
//@JsonIgnoreProperties({"email", "password", "phone", "address", "accountRole"})
@Getter
@Builder
public class AccountDto extends BaseTimeEntity {

    private Long id;

    @NotNull
    private String nickname;

    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String phone;

    private Address address;

    private Set<AccountRole> accountRole;

    private AccountStatus status;
}
