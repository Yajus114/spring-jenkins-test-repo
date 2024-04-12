package com.dawnbit.master.currency;

import com.dawnbit.entity.master.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

//    @Query(value = "select * from currency", nativeQuery = true)
//    List<Currency> fetchCurrency();

}
