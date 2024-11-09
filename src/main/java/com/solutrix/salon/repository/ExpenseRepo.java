package com.solutrix.salon.repository;


import com.solutrix.salon.dto.DashboardDTO;
import com.solutrix.salon.entity.ExpType;
import com.solutrix.salon.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ExpenseRepo extends JpaRepository<Expense,Integer> {

    @Query(value = "SELECT COALESCE(MAX(doc_no), 0) FROM my_expense", nativeQuery = true)
    Optional<Integer> findMaxDocNo();

    @Query(value = "SELECT COALESCE(MAX(voc_no), 0) FROM my_expense where brhid=:brhid", nativeQuery = true)
    Optional<Integer> findMaxVocNo(@Param("brhid") Integer brhid);

    List<Expense> findAllByBrhidIs(Integer brhid);

    @Query("select round(sum(coalesce(i.nettotal,0)),2) from Expense i where i.date>=:fromdate and i.date<=:todate and i.brhid=:brhid")
    Double findExpense(@Param("fromdate") java.sql.Date fromdate,@Param("todate") java.sql.Date todate,@Param("brhid") int brhid);

    @Query("select round(sum(coalesce(i.nettotal,0)),2) from Expense i where i.date>=:fromdate and i.date<=:todate")
    Double findExpenseAllBranch(@Param("fromdate") java.sql.Date fromdate,@Param("todate") java.sql.Date todate);

    @Query(value = "SELECT DATE_FORMAT(exp.date, '%b-%y') as month,SUM(exp.total) as totalExpense FROM my_expense exp WHERE exp.date>=:fromdate and exp.date<=:todate and exp.brhid=:brhid GROUP BY DATE_FORMAT(exp.date, '%b-%y')", nativeQuery = true)
    List<Object[]> findExpenseByMonth(@Param("fromdate") java.sql.Date fromdate,@Param("todate") java.sql.Date todate,@Param("brhid") int brhid);

    @Query(value = "SELECT DATE_FORMAT(exp.date, '%b-%y') as month,SUM(exp.total) as totalExpense FROM my_expense exp WHERE exp.date>=:fromdate and exp.date<=:todate GROUP BY DATE_FORMAT(exp.date, '%b-%y')", nativeQuery = true)
    List<Object[]> findExpenseByMonthAllBranch(@Param("fromdate") java.sql.Date fromdate,@Param("todate") java.sql.Date todate);

    @Query("select round (coalesce(sum(nettotal),0) ,2) from Expense where month(date)=:month and year(date)=:year")
    Double findSumTaxTotal(@Param("month") int month,@Param("year") int year);

    @Query("select round (coalesce(sum(nettotal),0) ,2) from Expense where month(date)=:month and year(date)=:year and brhid=:brhid")
    Double findSumTaxTotal(@Param("month") int month,@Param("year") int year,@Param("brhid") int brhid);

    @Query("SELECT COALESCE(et.refname, 'Unknown') refname,ROUND(SUM(COALESCE(ex.nettotal,0)),2) chartvalue FROM Expense ex LEFT JOIN ExpType et ON ex.exptype=et.docno WHERE ex.date>=:fromdate AND ex.date<=:todate and (:brhid is NULL or ex.brhid=:brhid) GROUP BY ex.exptype")
    List<Map<String,Object>> findExpenseByExptype(@Param("fromdate") java.sql.Date fromdate,@Param("todate") java.sql.Date todate,@Param("brhid") Integer brhid);

    @Query("SELECT COALESCE(hd.acname, 'Unknown') refname,ROUND(SUM(COALESCE(ex.nettotal,0)),2) chartvalue FROM Expense ex LEFT JOIN Account hd ON ex.expenseacno=hd.docno WHERE ex.date>=:fromdate AND ex.date<=:todate  and (:brhid is NULL or ex.brhid=:brhid) GROUP BY ex.expenseacno")
    List<Map<String,Object>> findExpenseByAccount(@Param("fromdate") java.sql.Date fromdate,@Param("todate") java.sql.Date todate,@Param("brhid") Integer brhid);

    @Query("select round (coalesce(sum(case when m.paytype=1 then m.nettotal else 0.0 end),0),2) as dailyexpcash,round (coalesce(sum(case when m.paytype=6 then m.nettotal else 0.0 end),0),2) as dailyexpcredit,round (coalesce(sum(case when m.paytype not in (1,6) then m.nettotal else 0.0 end),0),2) as dailyexpcard from Expense m where m.date=:dailydate")
    DashboardDTO findDailyExpCounter(@Param("dailydate") java.sql.Date dailydate);

    List<Expense> findAllByDate(@Param("dailydate") java.sql.Date dailydate);
}
