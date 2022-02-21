package com.paytmmoney.sipmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Investment {
    @Id
    @SequenceGenerator(
            name = "investment_sequence",
            sequenceName = "investment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "investment_sequence"
    )
    @Column(name = "folio_no")
    private Long folioNo;

    private Long shareCount;
    private BigDecimal investAmount;
    private BigDecimal currentAmount;
    private Date startDate;

    @OneToOne
    @JoinColumn(
            name = "scheme_id",
            referencedColumnName = "scheme_id"
    )
    private Scheme scheme;
}
