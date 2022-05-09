package com.paytmmoney.sipmanagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Scheme {
    @Id
    @Column(name = "scheme_id", length = 10)
    @NotEmpty(message = "Scheme id should be provided")
    private String schemeId;

    @NotEmpty(message = "Series should not be empty")
    private String series;

    @Column(name="scheme_name",nullable = false, unique = true)
    @NotEmpty(message = "Scheme Name should be provided")
    private String schemeName;

    @Column(name="isin_no", nullable = false)
    @NotEmpty(message = "ISIN No Should not be empty")
    private String isinNo;

    @Column(name="rta_scheme_code",nullable = false)
    @NotEmpty(message = "RTA Scheme Code Field should not be empty")
    private String rtaSchemeCode;

    @ManyToOne
    @JoinColumn(
            name="amc_id"
    )
    @JsonBackReference
    private Amc amc;
}
