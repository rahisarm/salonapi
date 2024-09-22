package com.solutrix.salon.repository;

import com.solutrix.salon.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BranchRepo extends JpaRepository<Branch,Integer> {

    List<Branch> findByStatusNot(int id);

    @Query(value = "select * from my_brch b where b.doc_no=:docno and b.status<>7", nativeQuery = true)
    Optional<Branch> findActiveById(@Param("docno") int docno);

    @Query(value = "SELECT COALESCE(MAX(doc_no), 0) FROM my_brch", nativeQuery = true)
    Optional<Integer> findMaxDocNo();

}
