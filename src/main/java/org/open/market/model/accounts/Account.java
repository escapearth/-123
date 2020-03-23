package org.open.market.model.accounts;

import lombok.*;
import org.open.market.common.BaseTimeEntity;
import org.open.market.model.Address;
import org.open.market.model.dto.AccountDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> accountRole;

    public void update(AccountDto accountDto) {
        this.nickname = accountDto.getNickname();
        this.password = accountDto.getPassword();
        this.accountRole = accountDto.getAccountRole();
        this.phone = accountDto.getPhone();
        this.email = accountDto.getEmail();
        this.address = accountDto.getAddress();
        // modifiedData
    }

}
