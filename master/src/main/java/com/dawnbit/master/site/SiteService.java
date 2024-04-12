package com.dawnbit.master.site;

import com.dawnbit.entity.master.Site;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import com.dawnbit.master.externalDTO.SiteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SiteService {
    Site saveOrUpdateSite(SiteDTO siteDTO);

    void deleteSite(Long id);

    List<Site> getAllSites();

    Optional<Site> getSiteById(Long id);

    boolean checkSiteNameDuplicacy(String siteName, long orgId);

    Page<?> fetchSiteData(SearchModelDTO searchModel, String organisationId);


    Page<Site> getPaginatedSites(Pageable pageable);

    Object fetchSiteByOrg(String orgId);
}
