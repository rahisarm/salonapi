package com.solutrix.salon.repository;

import com.solutrix.salon.entity.SubAcType;
import com.solutrix.salon.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubAcTypeRepo extends CrudRepository<SubAcType, Integer> {

    @Query(value = "SELECT COALESCE(MAX(doc_no), 0) FROM my_subactype", nativeQuery = true)
    Optional<Integer> findMaxDocNo();

    @Query(value = "SELECT COALESCE(MAX(voc_no), 0) FROM my_subactype where brhid=:brhid", nativeQuery = true)
    Optional<Integer> findMaxVocNo(@Param("brhid") int brhid);

    /*
    @Query(value = "SELECT * FROM my_subactype u WHERE u.status <> 7 AND u.brhid = :brhid", nativeQuery = true)
    List<User> findAllByBrhid(@Param("brhid") int brhid);*/

    Optional<SubAcType> findSubAcTypesByBrhidAndStatusNot(int brhid, int status);
}
