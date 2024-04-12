package com.dawnbit.master.resetPassword;

import com.dawnbit.entity.master.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordRepository extends JpaRepository<User, Long> {

    @Query("SELECT COUNT(u) FROM User u WHERE u.name = ?1")
    Long findExistingName(String name);

    @Query("SELECT u FROM User u WHERE u.name = ?1")
    User getUserByName(String name);

    @Query("SELECT u FROM User u WHERE u.userId = ?1")
    User getUserDetails(long id);
}
