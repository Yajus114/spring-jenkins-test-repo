package com.dawnbit.master.site;

import com.dawnbit.entity.master.Address;
import com.dawnbit.entity.master.OrganisationMaster;
import com.dawnbit.entity.master.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SiteRepo extends JpaRepository<Site, Long> {

    boolean existsBySiteNameAndOrg(String siteName, OrganisationMaster org);
    @Query(value = "select count(*) as site_count from site_details", nativeQuery = true)
    Object getSiteCount();


    boolean existsBySiteNameIgnoreCaseAndOrgAndIdNot(String siteName, OrganisationMaster org, long id);
    @Query(value = "select * from site_details where org_id =?1", nativeQuery = true)
    List<Site> fetchSiteByOrg(String organisationId);
    @Query(value = "select count(*) from site_details where org_id =?1  AND status = 'ACTIVE'", nativeQuery = true)
    String getSiteCountByOrgId(long id);
    @Query(value = "SELECT COUNT(*) FROM site_details WHERE id =?1  AND status = 'ACTIVE'", nativeQuery = true)
    Object getSiteCountBySiteId (long id);
    @Query(value = "SELECT COUNT(*) FROM site_details WHERE status = 'ACTIVE'", nativeQuery = true)
    int getActiveSiteCount ();
    @Query(value = "SELECT COUNT(*) FROM site_details WHERE status = 'INACTIVE'", nativeQuery = true)
    int getInactiveSiteCount ();
}
