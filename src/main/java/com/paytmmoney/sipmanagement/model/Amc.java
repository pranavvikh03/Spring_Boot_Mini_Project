package com.paytmmoney.sipmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Amc {

    @Id
    @Column(name = "amc_id", length = 10)
    private String amcId;
    private String name;

    @OneToMany(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "amc_id",
            referencedColumnName = "amc_id"
    )
    private List<Scheme> schemes;
}
