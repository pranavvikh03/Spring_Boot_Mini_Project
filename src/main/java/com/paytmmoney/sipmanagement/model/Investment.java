package com.paytmmoney.sipmanagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
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

    @NotNull
    @Min(1)
    private Long shareCount;

    @Column(nullable = false)
    @DecimalMin(message = "Not Allowed", value = "1.0")
    private BigDecimal investAmount;

    @Column(nullable = false)
    @DecimalMin(message = "Not Allowed", value = "1.0")
    private BigDecimal currentAmount;

//    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date startDate;

    @OneToOne
    @JoinColumn(
            name = "scheme_id",
            referencedColumnName = "scheme_id"
    )
    private Scheme scheme;

    @ManyToOne
    @JoinColumn(
            name = "portfolio_id"
    )
    @JsonBackReference
    private Portfolio portfolio;
}
