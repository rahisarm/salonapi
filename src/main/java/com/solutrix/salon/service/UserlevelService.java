package com.solutrix.salon.service;

import com.solutrix.salon.entity.Userlevel;
import com.solutrix.salon.repository.UserlevelRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserlevelService {

    @Autowired
    private UserlevelRepo repo;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Userlevel> getAllUserlevels() {
        return repo.findByStatus(3);
    }

}
