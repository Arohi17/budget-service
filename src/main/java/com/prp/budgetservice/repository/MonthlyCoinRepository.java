package com.prp.budgetservice.repository;

import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import com.prp.budgetservice.entity.MonthlyCoin;
import org.springframework.data.repository.query.Param;

public interface MonthlyCoinRepository extends ArangoRepository<MonthlyCoin, String> {

    MonthlyCoin findByUserIdAndMonthAndYear(String userId, int month, int year);

   @Query("FOR c IN monthly_coins FILTER c.userId == @userId SORT c.year DESC, c.month DESC LIMIT 1 RETURN c")
   MonthlyCoin findLatestForUser(@Param("userId") String userId);

    //MonthlyCoin findTopByUserIdOrderByYearDescMonthDesc(String userId);
}

