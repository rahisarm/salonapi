package com.solutrix.salon.repository;


import com.solutrix.salon.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuditRepo extends JpaRepository<Audit,Integer> {

}
