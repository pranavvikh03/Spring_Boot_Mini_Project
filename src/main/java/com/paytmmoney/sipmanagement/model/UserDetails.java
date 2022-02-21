package com.paytmmoney.sipmanagement.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import javax.persistence.ElementCollection;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {
    @Column(name = "pan_card_no", nullable = false)
    private String panCardNo;

    @ElementCollection
    @CollectionTable(
        name = "bank_accounts",
        joinColumns = @JoinColumn(name = "user_id"),
        uniqueConstraints = @UniqueConstraint(
            name = "bank_account_unique",
            columnNames = "bank_account"
        )
    )
    @Column(name = "bank_account")
    private List<String> bankAccounts;

    @Column(nullable = false)
    private String email;

    private String name;
}
