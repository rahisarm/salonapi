package com.solutrix.salon.repository;


import com.solutrix.salon.entity.Employee;
import com.solutrix.salon.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VendorRepo extends JpaRepository<Vendor,Integer> {

    @Query(value = "SELECT COALESCE(MAX(doc_no), 0) FROM my_vendor", nativeQuery = true)
    Optional<Integer> findMaxDocNo();

    @Query(value = "SELECT COALESCE(MAX(voc_no), 0) FROM my_vendor where brhid=:brhid", nativeQuery = true)
    Optional<Integer> findMaxVocNo(@Param("brhid") int brhid);

    Optional<Vendor> findByRefname(String refname);

    List<Vendor> findAllByBrhidIsAndAndStatusNot(Integer brhid, Integer status);
}
