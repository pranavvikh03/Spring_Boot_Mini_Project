package com.paytmmoney.sipmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paytmmoney.sipmanagement.validation.MaxSizeValidator;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails implements Serializable {

    /*
    * Pan Card Validation
    *   1. It should be empty
    *   2. It follows standard pan card no pattern. i.e
    *           - It should be ten characters long.
                - The first five characters should be any upper case alphabets.
                - The next four-characters should be any number from 0 to 9.
                - The last(tenth) character should be any upper case alphabet.
                - It should not contain any white spaces.
    * */
    @Column(name = "pan_card_no", nullable = false)
    @NotEmpty(message = "Pan Card No Should not be Empty")
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Enter Valid Pan Card No.")
    private String panCardNo;

    /*
    * Bank Accounts Validation
    *   1. All accounts should be unique
    *   2. One user can not add more than 5 accounts
    * */
    @ElementCollection
    @CollectionTable(
        name = "bank_accounts",
        joinColumns = @JoinColumn(name = "user_id"),
        uniqueConstraints = @UniqueConstraint(
            name = "bank_account_unique",
            columnNames = "bank_account"
        )
    )
    @Fetch(FetchMode.JOIN)
    @Column(name = "bank_account")
    @NotEmpty(message = "At least 1 Bank Account should provided")
    @MaxSizeValidator //For checking user can only add at most 5 accounts
    private List<String> bankAccounts;

    /*
    *   Email Validation
    */
    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Email Should not be Empty")
    @Email(message = "Enter Valid Email")
    private String email;

    /*
    * User Name Validation that it should not be empty and should not be less than 2 size
    * */
    @NotEmpty
    @Size(min = 2, message = "Name size should be more than 2", max = 100)
    private String name;
}
