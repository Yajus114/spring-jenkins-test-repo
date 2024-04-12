package com.dawnbit.security.repository;

import com.dawnbit.entity.master.UserLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, Long> {
    @Query(value = "SELECT IF(COUNT(*) > 0, true, false) FROM user_login_history WHERE ip_address = :remoteAddr AND modified_date >= DATE_SUB(NOW(), INTERVAL 10 SECOND)", nativeQuery = true)
    @SuppressWarnings("all")
    long existsByIpWithinLast10Seconds(@Param("remoteAddr") String remoteAddr);
}
