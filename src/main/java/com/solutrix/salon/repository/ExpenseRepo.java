package com.solutrix.salon.repository;


import com.solutrix.salon.entity.ExpType;
import com.solutrix.salon.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepo extends JpaRepository<Expense,Integer> {

    @Query(value = "SELECT COALESCE(MAX(doc_no), 0) FROM my_expense", nativeQuery = true)
    Optional<Integer> findMaxDocNo();

    @Query(value = "SELECT COALESCE(MAX(voc_no), 0) FROM my_expense where brhid=:brhid", nativeQuery = true)
    Optional<Integer> findMaxVocNo(@Param("brhid") int brhid);

    List<Expense> findAllByBrhidIs(Integer brhid);
}
