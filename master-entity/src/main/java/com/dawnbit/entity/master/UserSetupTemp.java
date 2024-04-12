package com.dawnbit.entity.master;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class UserSetupTemp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "process_id")
    private long processId;

    private Long userId;

    private String userName;

    private String userType;

    private String organisation;

    private String contactNo;

    private String emailId;

    private String status;

    private String country;

    private String address;

    @Column(
            name = "errors",
            length = 1000
    )
    private String errors;
    private String createdBy;
    private String modifiedBy;
    private Date createdDate;
    private Date modifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = new Date();
//		this.createdBy = (String) SecurityUtils.getSubject().getPrincipal();
    }

    @PreUpdate
    protected void onModify() {
        this.modifiedDate = new Date();
//		this.modifiedBy = (String) SecurityUtils.getSubject().getPrincipal();

    }

    public UserSetupTemp(long processId, String userName, String userType, String organisation, String contactNo, String emailId, String status, String country,String address) {
        this.processId = processId;
        this.userName = userName;
        this.userType = userType;
        this.organisation = organisation;
        this.contactNo = contactNo;
        this.emailId = emailId;
        this.status = status;
        this.country = country;
        this.address=address;
    }
}
