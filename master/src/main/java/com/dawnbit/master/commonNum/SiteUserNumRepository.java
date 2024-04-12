package com.dawnbit.master.commonNum;

import com.dawnbit.entity.master.SiteUserNum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteUserNumRepository extends JpaRepository<SiteUserNum, Long> {
    SiteUserNum findBySiteKeywordIdIgnoreCase(String siteKeywordId);
}
