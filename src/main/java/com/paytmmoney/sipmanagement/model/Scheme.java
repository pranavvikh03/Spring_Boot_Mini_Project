package com.paytmmoney.sipmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Scheme {
    @Id
    @Column(name = "scheme_id", length = 10)
    private String schemeId;

    private String series;

    @Column(name="scheme_name")
    private String schemeName;

    @Column(name="isin_no")
    private String isinNo;

    @Column(name="amc_scheme_code")
    private String amcSchemeCode;

    @Column(name="rta_scheme_code")
    private String rtaSchemeCode;
}
