package com.dawnbit.master.externalDTO;

import com.dawnbit.entity.master.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDropDownDTO {
    private Long personId;

    private String personFullName;


    public PersonDropDownDTO(Person person) {
        this.personId = person.getId();
        this.personFullName = person.getFirstName() + " " + person.getLastName();
    }
}
