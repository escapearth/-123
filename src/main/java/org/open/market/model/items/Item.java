package org.open.market.model.items;

import lombok.*;
import org.open.market.model.accounts.Account;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(exclude = "name")
// access = AccessLevel.PROTECTED : 객체 생성 시 안전성을 어느 정도 보장받을 수 있다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL) // DB 의 Cascade Keyword
    private List<ItemCategory> itemCategories = new ArrayList<>();
}
