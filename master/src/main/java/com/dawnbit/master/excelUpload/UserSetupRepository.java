package com.dawnbit.master.excelUpload;

import com.dawnbit.entity.master.UserSetupTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSetupRepository extends JpaRepository<UserSetupTemp, Long> {
    List<UserSetupTemp> findByProcessId(long processId);


    List<UserSetupTemp> findByProcessIdAndErrorsIsNull(long processId);

}
