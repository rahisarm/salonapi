package com.solutrix.salon.repository;


import com.solutrix.salon.entity.DailyBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DailyBalRepo extends JpaRepository<DailyBalance,Integer> {

    @Query(value = "SELECT COALESCE(MAX(doc_no), 0) FROM my_dailybal", nativeQuery = true)
    Optional<Integer> findMaxDocNo();

    Optional<DailyBalance> findByDate(java.sql.Date date);

    @Query(value = "SELECT ROUND(COALESCE(SUM(taxtotal),0),2) amount FROM my_invm WHERE DATE=:date AND (:brhid IS NULL OR brhid=:brhid)",nativeQuery = true)
    Double findInvoiceByDate(@Param("date") java.sql.Date date, @Param("brhid") Integer brhid);

    @Query(value = "SELECT ROUND(COALESCE(SUM(total),0),2) amount FROM my_expense WHERE DATE=:date AND (:brhid IS NULL OR brhid=:brhid)",nativeQuery = true)
    Double findExpenseByDate(@Param("date") java.sql.Date date, @Param("brhid") Integer brhid);

    @Query(value = "SELECT ROUND(COALESCE(closingbalance,0),2) amount FROM my_dailybal WHERE DATE=DATE_SUB(:date,INTERVAL 1 DAY) AND (:brhid IS NULL OR brhid=:brhid)",nativeQuery = true)
    Double findLastClosingBalance(@Param("date") java.sql.Date date, @Param("brhid") Integer brhid);
}
