package com.solutrix.salon.service;

import com.solutrix.salon.entity.Account;
import com.solutrix.salon.entity.Trno;
import com.solutrix.salon.exception.ResourceNotFoundException;
import com.solutrix.salon.repository.AccountRepo;
import com.solutrix.salon.repository.TrnoRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TrnoService {

    @Autowired
    private TrnoRepo repo;

    public int getNewTrno(String dtype){
        Trno trno = new Trno();
        trno.setTrtype(dtype);
        repo.save(trno);
        return trno.getTrno();
    }
}
