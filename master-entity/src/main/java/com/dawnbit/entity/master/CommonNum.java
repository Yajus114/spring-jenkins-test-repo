package com.dawnbit.entity.master;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
public class CommonNum {

    @Id
    private long id;

    private String name;

    private long countRows;

}
