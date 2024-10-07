package com.solutrix.salon.repository;


import com.solutrix.salon.entity.Account;
import com.solutrix.salon.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConfigRepo extends JpaRepository<Config,Integer> {

    Optional<Config> findByFieldname(String fieldname);

}
