package com.solutrix.salon.repository;


import com.solutrix.salon.entity.Account;
import com.solutrix.salon.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee,Integer> {

    @Query(value = "SELECT COALESCE(MAX(doc_no), 0) FROM my_empm", nativeQuery = true)
    Optional<Integer> findMaxDocNo();

    @Query(value = "SELECT COALESCE(MAX(voc_no), 0) FROM my_empm where brhid=:brhid", nativeQuery = true)
    Optional<Integer> findMaxVocNo(@Param("brhid") int brhid);

    Optional<Employee> findByRefname(String refname);

    List<Employee> findAllByBrhidIsAndAndStatusNot(Integer brhid, Integer status);

    @Query("select count(*) from Employee where active=true and brhid=:brhid")
    int countActiveByBrhid(@Param("brhid") int brhid);

    @Query("select count(*) from Employee where active=true")
    int countActive();

}
