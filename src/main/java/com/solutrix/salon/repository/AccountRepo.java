package com.solutrix.salon.repository;


import com.solutrix.salon.entity.Account;
import com.solutrix.salon.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account,Integer> {

    @Query(value = "SELECT COALESCE(MAX(doc_no), 0) FROM my_head", nativeQuery = true)
    Optional<Integer> findMaxDocNo();

    @Query(value = "SELECT COALESCE(MAX(voc_no), 0) FROM my_head where brhid=:brhid", nativeQuery = true)
    Optional<Integer> findMaxVocNo(@Param("brhid") int brhid);

    Optional<Account> findByAcname(String acname);

    List<Account> findAllByBrhidIsAndAndStatusNot(Integer brhid, Integer status);

    List<Account> findAccountsByBrhidIsAndStatusNotAndActypeEqualsIgnoreCase(Integer brhid, Integer status,String actype);
}
