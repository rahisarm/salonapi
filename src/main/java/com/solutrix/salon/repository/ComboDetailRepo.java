package com.solutrix.salon.repository;


import com.solutrix.salon.dto.ComboDetailDTO;
import com.solutrix.salon.entity.ComboDetail;
import com.solutrix.salon.entity.ComboMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ComboDetailRepo extends JpaRepository<ComboDetail,Integer> {

    @Query(value = "SELECT COALESCE(MAX(doc_no), 0) FROM my_combodet", nativeQuery = true)
    Optional<Integer> findMaxDocNo();

    void deleteAllByComboMaster(ComboMaster comboMaster);
}
