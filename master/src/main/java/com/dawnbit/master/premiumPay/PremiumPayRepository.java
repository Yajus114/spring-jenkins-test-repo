package com.dawnbit.master.premiumPay;

import com.dawnbit.entity.master.PremiumPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiumPayRepository extends JpaRepository<PremiumPay, Long> {
}
