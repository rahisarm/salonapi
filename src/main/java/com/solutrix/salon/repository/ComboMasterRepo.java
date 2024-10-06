package com.solutrix.salon.repository;


import com.solutrix.salon.dto.ComboMasterDTO;
import com.solutrix.salon.entity.Account;
import com.solutrix.salon.entity.ComboMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ComboMasterRepo extends JpaRepository<ComboMaster,Integer> {

    @Query(value = "SELECT COALESCE(MAX(doc_no), 0) FROM my_combomaster", nativeQuery = true)
    Optional<Integer> findMaxDocNo();

    @Query(value = "SELECT COALESCE(MAX(voc_no), 0) FROM my_combomaster where brhid=:brhid", nativeQuery = true)
    Optional<Integer> findMaxVocNo(@Param("brhid") int brhid);

    List<ComboMaster> findAllByBrhidIsAndAndStatusNot(Integer brhid, Integer status);

}
