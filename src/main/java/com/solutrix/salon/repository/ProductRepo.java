package com.solutrix.salon.repository;

import com.solutrix.salon.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product,Integer> {

    @Query(value = "SELECT COALESCE(MAX(doc_no), 0) FROM my_product", nativeQuery = true)
    Optional<Integer> findMaxDocNo();

    @Query(value = "SELECT COALESCE(MAX(voc_no), 0) FROM my_product where brhid=:brhid", nativeQuery = true)
    Optional<Integer> findMaxVocNo(@Param("brhid") int brhid);

    List<Product> findAllByBrhidIsAndAndStatusNot(Integer brhid, Integer status);
}
