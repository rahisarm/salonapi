package com.solutrix.salon.repository;


import com.solutrix.salon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {

    @Query(value = "SELECT * FROM my_user u WHERE u.status <> 7 AND u.brhid = :brhid", nativeQuery = true)
    List<User> findAllActiveUsersByBranch(@Param("brhid") int brhid);

    @Query(value = "SELECT COALESCE(MAX(doc_no), 0) FROM my_user", nativeQuery = true)
    Optional<Integer> findMaxDocNo();

    @Query(value = "SELECT COALESCE(MAX(voc_no), 0) FROM my_user where brhid=:brhid", nativeQuery = true)
    Optional<Integer> findMaxVocNo(@Param("brhid") int brhid);

    Optional<User> findByUsername(String username);

}
