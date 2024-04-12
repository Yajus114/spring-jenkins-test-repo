package com.dawnbit.security.user;

import com.dawnbit.entity.master.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetails, Long> {
    UserDetails findByIdAndStatus(long userId, String active);
//    @Query("select u from User u where u.userId =?1 ")
//    User getUserDetails(String username);

}
