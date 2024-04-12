package com.dawnbit.entity.master;


import com.dawnbit.entity.master.common.utils.CommonUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_login")
public class User {

    private final boolean deleted = false;
    private final boolean restricted = false;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String name;
    private String password;
    private String roles;
    private Date passwordExpiryDatetime;
    private String recoverPasswordOtp;
    private Date otpExpiryDatetime;
    private String modifiedBy;
    private String createdBy;
    private Date createdDate;
    private long userId;
    private Date modifiedDate;
    private String remarks;

    private boolean isEnabled;

    private boolean isNotFirstTimeLoggedIn = false;

    public User(final Long id) {
        this.id = id;
    }

    @PrePersist
    private void persistDates() {
        this.createdDate = new Date();
        this.modifiedDate = new Date();
        this.createdBy = CommonUtils.getPrincipal();
        this.modifiedBy = this.createdBy;
    }

    /**
     * To create record when the data was updated last and by whom
     */
    @PreUpdate
    private void updateDates() {
        this.modifiedDate = new Date();
        this.modifiedBy = CommonUtils.getPrincipal();
    }

//    public boolean isCredentialsNonExpired() {
//        final Calendar currentDateCalendar = Calendar.getInstance();
//        currentDateCalendar.setTime(new Date());
//        currentDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
//        currentDateCalendar.set(Calendar.MINUTE, 0);
//        currentDateCalendar.set(Calendar.SECOND, 0);
//        currentDateCalendar.set(Calendar.MILLISECOND, 0);
//        currentDateCalendar.add(Calendar.DATE, 1);
//        return this.getPasswordExpiryDatetime() == null || currentDateCalendar.before(this.getPasswordExpiryDatetime());
//    }

//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    public boolean isAccountNonExpired() {
//        return true;
//
//    }

}
