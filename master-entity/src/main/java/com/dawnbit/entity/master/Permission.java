package com.dawnbit.entity.master;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DB-0079
 */
@Data
@Table(name = "permission")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * permission name
     */
    private String name;

    /**
     * application name
     */
    private String application; //Application name

    /**
     * the module name to which the permission of the application belongs to
     */
    private String moduleName;

    /**
     * description
     */
    //@Size(max = 255, message = "Max 255 characters are allowed")
    private String description;

}
