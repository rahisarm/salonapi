package com.solutrix.salon.repository;


import com.solutrix.salon.dto.DashboardDTO;
import com.solutrix.salon.entity.Account;
import com.solutrix.salon.entity.InvoiceMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface InvoiceMasterRepo extends JpaRepository<InvoiceMaster,Integer> {

    @Query(value = "SELECT COALESCE(MAX(doc_no), 0) FROM my_invm", nativeQuery = true)
    Optional<Integer> findMaxDocNo();

    @Query(value = "SELECT COALESCE(MAX(voc_no), 0) FROM my_invm where brhid=:brhid", nativeQuery = true)
    Optional<Integer> findMaxVocNo(@Param("brhid") int brhid);

    @Query("select round(sum(coalesce(i.subtotal,0)),2) from InvoiceMaster i where i.date>=:fromdate and i.date<=:todate and i.brhid=:brhid")
    Double findSumSubtotal(@Param("fromdate") java.sql.Date fromdate,@Param("todate") java.sql.Date todate,@Param("brhid") int brhid);

    @Query("select round(sum(coalesce(i.subtotal,0)),2) from InvoiceMaster i where i.date>=:fromdate and i.date<=:todate")
    Double findSumSubtotalAllBranch(@Param("fromdate") java.sql.Date fromdate,@Param("todate") java.sql.Date todate);

    @Query(value = "SELECT DATE_FORMAT(inc.date, '%b-%y') as month,SUM(inc.taxtotal) as totalIncome FROM my_invm inc WHERE inc.date>=:fromdate and inc.date<=:todate and inc.brhid=:brhid GROUP BY DATE_FORMAT(inc.date, '%b-%y')", nativeQuery = true)
    List<Object[]> findIncomeByMonth(@Param("fromdate") java.sql.Date fromdate,@Param("todate") java.sql.Date todate,@Param("brhid") int brhid);

    @Query(value = "SELECT DATE_FORMAT(inc.date, '%b-%y') as month,SUM(inc.taxtotal) as totalIncome FROM my_invm inc WHERE inc.date>=:fromdate and inc.date<=:todate GROUP BY DATE_FORMAT(inc.date, '%b-%y')", nativeQuery = true)
    List<Object[]> findIncomeByMonthAllBranch(@Param("fromdate") java.sql.Date fromdate,@Param("todate") java.sql.Date todate);

    @Query("select round (coalesce(sum(taxtotal),0) ,2) from InvoiceMaster where month(date)=:month and year(date)=:year")
    Double findSumTaxTotal(@Param("month") int month,@Param("year") int year);

    @Query("select round (coalesce(sum(taxtotal),0) ,2) from InvoiceMaster where month(date)=:month and year(date)=:year and brhid=:brhid")
    Double findSumTaxTotal(@Param("month") int month,@Param("year") int year,@Param("brhid") int brhid);

    /*@Query("select round (coalesce(sum(case when m.paytype=1 then m.taxtotal else 0.0 end),0),2) as dailyinvcash,round (coalesce(sum(case when m.paytype=6 then m.taxtotal else 0.0 end),0),2) as dailyinvcredit,round (coalesce(sum(case when m.paytype not in (1,6) then m.taxtotal else 0.0 end),0),2) as dailyinvcard from InvoiceMaster m where m.date=:dailydate")
    DashboardDTO findDailyInvCounter(@Param("dailydate") java.sql.Date dailydate);*/

    List<InvoiceMaster> findAllByDate(@Param("dailydate") java.sql.Date dailydate);
    @Query(value = "SELECT base.*,ROUND(COALESCE(base.salary,0)+COALESCE(base.nightbonus,0)+COALESCE(base.workbonus,0),2) totalsalary FROM (\n" +
            " SELECT emp.doc_no empdocno,emp.refname empname,emp.salary,ROUND(SUM(COALESCE(nightbonus,0)),2) nightbonus,ROUND(SUM(COALESCE(workbonus,0)),2) workbonus FROM my_invm inv LEFT JOIN my_empm emp ON inv.empid=emp.doc_no \n" +
            " WHERE MONTH(inv.date)=MONTH(:payrolldate) AND YEAR(inv.date)=YEAR(:payrolldate) GROUP BY emp.doc_no) base",nativeQuery = true)
    List<Map<String,Object>> findAllSalaryByPayroll(@Param("payrolldate") java.sql.Date payrolldate);

}
