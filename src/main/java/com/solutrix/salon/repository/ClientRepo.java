package com.solutrix.salon.repository;


import com.solutrix.salon.dto.UserDTO;
import com.solutrix.salon.entity.Client;
import com.solutrix.salon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepo extends JpaRepository<Client,Integer> {

    @Query(value = "SELECT COALESCE(MAX(cldocno), 0) FROM my_acbook", nativeQuery = true)
    Optional<Integer> findMaxDocNo();

    @Query(value = "SELECT COALESCE(MAX(voc_no), 0) FROM my_acbook where brhid=:brhid", nativeQuery = true)
    Optional<Integer> findMaxVocNo(@Param("brhid") int brhid);

    Optional<Client> findByRefname(String refname);

    List<Client> findAllByBrhidIsAndAndStatusNot(Integer brhid, Integer status);
}
