package com.dawnbit.master.userdetails;

import com.dawnbit.entity.master.OrganisationMaster;
import com.dawnbit.entity.master.User;
import com.dawnbit.entity.master.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetails, Long> {


    @Query(value = "SELECT * FROM user_details where id=?1 ", nativeQuery = true)
    UserDetails findByUserId(long id);


//    UserDetails findAllByUserKeywordIdIgnoreCase(String userKeywordId);
//
//    UserDetails findByEmailId(String emailId);

    @Query(value = "select * from user_details where email_id=?1 and organisation_id=?2", nativeQuery = true)
    List<UserDetails> findByEmailAndOrganizationId(String emailId, String orgId);

    @Query("Select s from UserDetails s where s.Id=?1")
    UserDetails findByIds(long id);

    @Query("select u from User u where u.userId =?1 ")
    User getUserDetails(String username);


    @Query(value = "SELECT * FROM user_login where name=?1 ", nativeQuery = true)
    User findByNameId(String name);

    //    List<UserDetails> findByEmailIdAndOrganisationMaster(String email, OrganisationMaster orgObj);
    List<UserDetails> findByOrganisationAndEmailId(OrganisationMaster organisation, String emailId);

//    List<UserDetails> findByEmailIdAndOrganisationOrganizationMaster(String emailId, OrganisationMaster organisationMaster);


    @Query(value = "select count(id) from user_details where organisation_id=?1 ", nativeQuery = true)
    int getAllUsersByOrgId(String organisationId);


    @Query(value = "select count(id) from user_details where organisation_id=?1 and status='ACTIVE' ", nativeQuery = true)
    int getUserCountByOrgId(long id);

    @Query(value = "select count(id) from user_details where organisation_id=?1 and status='INACTIVE' ", nativeQuery = true)
    int getInactiveUserCountByOrgId(long id);


    List<UserDetails> getUserDetailsByOrganisationAndUserKeywordId(OrganisationMaster organisationMaster, String keyword);

    boolean existsByUserKeywordId(String userId);

    @Query(value = "select * from user_details where employee_id=?1", nativeQuery = true)
    UserDetails getUserDetailsByEmployeeId(Long employeeId);
}
