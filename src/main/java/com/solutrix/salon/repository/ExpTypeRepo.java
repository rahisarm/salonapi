package com.solutrix.salon.repository;


import com.solutrix.salon.entity.Account;
import com.solutrix.salon.entity.ExpType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExpTypeRepo extends JpaRepository<ExpType,Integer> {

    @Query(value = "SELECT COALESCE(MAX(doc_no), 0) FROM my_exptype", nativeQuery = true)
    Optional<Integer> findMaxDocNo();

    @Query(value = "SELECT COALESCE(MAX(voc_no), 0) FROM my_exptype where brhid=:brhid", nativeQuery = true)
    Optional<Integer> findMaxVocNo(@Param("brhid") int brhid);


    List<ExpType> findAllByBrhidIs(Integer brhid);

}
