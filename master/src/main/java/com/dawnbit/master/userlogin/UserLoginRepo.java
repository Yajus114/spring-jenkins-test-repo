package com.dawnbit.master.userlogin;


import com.dawnbit.entity.master.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLoginRepo extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM user_login where user_id=?1", nativeQuery = true)
    User findByUserId(long id);

    Optional<User> findByName(String username);

    Optional<User> findByUsername(String username);

    @Query("Select u FROM User u where u.name=?1")
    User findUser(String name);
}
