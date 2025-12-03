package com.prp.budgetservice.repository;


import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import com.prp.budgetservice.entity.MonthBudget;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonthBudgetRepository extends ArangoRepository<MonthBudget,String> {

    Optional<MonthBudget> findByUserIdAndMonthAndYear(String userId, Integer month, Integer year);

    List<MonthBudget> findByUserId(String userId);

    @Query("""
                FOR b IN month_budget
                    FILTER b.userId == @userId
                    SORT b.year DESC, b.month DESC
                    LIMIT 1
                    RETURN b
            """)
    MonthBudget findLastMonth(@Param("userId") String userId);
}
