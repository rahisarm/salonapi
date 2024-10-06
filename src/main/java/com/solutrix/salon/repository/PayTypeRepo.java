package com.solutrix.salon.repository;


import com.solutrix.salon.entity.ExpType;
import com.solutrix.salon.entity.PayType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PayTypeRepo extends JpaRepository<PayType,Integer> {

    @Query(value = "SELECT COALESCE(MAX(doc_no), 0) FROM my_paytype", nativeQuery = true)
    Optional<Integer> findMaxDocNo();

}
