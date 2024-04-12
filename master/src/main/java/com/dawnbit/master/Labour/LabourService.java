package com.dawnbit.master.Labour;

//import com.dawnbit.common.exception.CustomException; // comment this out if throwing error

import com.dawnbit.entity.master.Skills;
import com.dawnbit.master.externalDTO.LabourDTO;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface LabourService {
    /**
     * @param labourDTO
     * @return
     * @throws CustomException
     * @author DB-CPU009
     */
    LabourDTO addUpdateLabour(LabourDTO labourDTO);

    /**
     * @param searchModel
     * @return
     * @author DB-CPU009
     */
    Page<?> fetchLabourData(SearchModelDTO searchModel);

    /**
     * @param labourId
     * @return
     * @author DB-CPU009
     */
    LabourDTO getLabourDataById(Long labourId);

    Skills getCraftAndSkillDetails(Long labourId);

    Long isCraftSkillDuplicate(Long labourId, Long craftId, Long skillId);
}
