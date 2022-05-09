package com.paytmmoney.sipmanagement.elasticsearch;

import lombok.Data;

@Data
public class SearchRequestDto {

    private String field;
    private String searchTerm;
}
