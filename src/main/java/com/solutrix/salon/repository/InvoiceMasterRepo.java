package com.solutrix.salon.repository;


import com.solutrix.salon.entity.Account;
import com.solutrix.salon.entity.InvoiceMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InvoiceMasterRepo extends JpaRepository<InvoiceMaster,Integer> {

    @Query(value = "SELECT COALESCE(MAX(doc_no), 0) FROM my_invm", nativeQuery = true)
    Optional<Integer> findMaxDocNo();

    @Query(value = "SELECT COALESCE(MAX(voc_no), 0) FROM my_invm where brhid=:brhid", nativeQuery = true)
    Optional<Integer> findMaxVocNo(@Param("brhid") int brhid);

    @Query("select sum(i.subtotal) from InvoiceMaster i where i.date>=:fromdate and i.date<=:todate and i.brhid=:brhid")
    Double findSumSubtotal(@Param("fromdate") java.sql.Date fromdate,@Param("todate") java.sql.Date todate,@Param("brhid") int brhid);

    @Query("select sum(i.subtotal) from InvoiceMaster i where i.date>=:fromdate and i.date<=:todate")
    Double findSumSubtotalAllBranch(@Param("fromdate") java.sql.Date fromdate,@Param("todate") java.sql.Date todate);


}
