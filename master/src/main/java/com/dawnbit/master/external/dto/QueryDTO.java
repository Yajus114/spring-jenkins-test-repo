package com.dawnbit.master.external.dto;

import lombok.Data;

@Data
public class QueryDTO {
    private String queryField;
    private String queryType;
    private String fieldType;
    private String value;

}
