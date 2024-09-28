package com.solutrix.salon.repository;

import com.solutrix.salon.entity.Branch;
import com.solutrix.salon.entity.Userlevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserlevelRepo extends JpaRepository<Userlevel, Integer> {
    List<Userlevel> findByStatus(int id);
}
