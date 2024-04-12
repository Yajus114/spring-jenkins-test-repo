package com.dawnbit.master.external.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermissionDTO {
    long id;

    String name;

    String moduleName;

    String application;

    String description;

    boolean perChecked;

}
