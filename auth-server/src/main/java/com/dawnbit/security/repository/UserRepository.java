package com.dawnbit.security.repository;

import com.dawnbit.entity.master.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);

    /**
     * @param username
     * @return
     */
    Optional<User> findByUsername(String username);

}
