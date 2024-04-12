package com.dawnbit.common.utils.excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelFormat {

    private int index;

    private String name;

    private List<String> validations;

}
