package com.paytmmoney.sipmanagement.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Portfolio {
    @Id
    @SequenceGenerator(
        name = "portfolio_sequence",
        sequenceName = "portfolio_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "portfolio_sequence"
    )
    @Column(name = "portfolio_id")
    private Long portfolioId;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "portfolio",
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<@Valid Investment> investments;

    @OneToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "user_id"
    )

    private User user;
}
