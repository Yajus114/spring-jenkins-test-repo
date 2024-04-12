package com.dawnbit.entity.master;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class SiteUserNum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String siteKeywordId;
    private long userCount;

}
