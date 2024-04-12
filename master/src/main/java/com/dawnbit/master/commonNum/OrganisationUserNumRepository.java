package com.dawnbit.master.commonNum;

import com.dawnbit.entity.master.OrganisationUserNum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationUserNumRepository extends JpaRepository<OrganisationUserNum, Long> {
    OrganisationUserNum findByOrgKeywordIdIgnoreCase(String orgKeywordId);
}
