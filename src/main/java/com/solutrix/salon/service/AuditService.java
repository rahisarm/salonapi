package com.solutrix.salon.service;

import com.solutrix.salon.entity.Audit;
import com.solutrix.salon.repository.AuditRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditService {

    @Autowired
    private AuditRepo repo;

    @Transactional(propagation = Propagation.REQUIRED)
    public Audit createAudit(Audit audit) {
        return repo.save(audit);
    }
}
