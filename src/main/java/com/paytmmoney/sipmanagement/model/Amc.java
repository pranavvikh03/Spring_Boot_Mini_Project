package com.paytmmoney.sipmanagement.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Amc {

    @Id
    @Column(name = "amc_id", length = 10)
    @NotEmpty(message = "AMC Id is Required")
    private String amcId;


    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Amc Name should not be Empty")
    @Size(min = 4 , max = 500)
    private String name;

//    @JoinColumn(
//            name = "amc_id",
//            referencedColumnName = "amc_id"
//    )
    @OneToMany(
            mappedBy = "amc",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<@Valid Scheme> schemes;
}
