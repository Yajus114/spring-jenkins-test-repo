package com.dawnbit.master.commonNum;

import com.dawnbit.entity.master.CommonNum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonNumRepository extends JpaRepository<CommonNum, Long> {


    CommonNum findByName(String name);
}
