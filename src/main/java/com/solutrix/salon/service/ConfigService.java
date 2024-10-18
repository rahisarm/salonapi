package com.solutrix.salon.service;

import com.solutrix.salon.entity.Config;
import com.solutrix.salon.repository.ConfigRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigService {

    @Autowired
    private ConfigRepo repo;

    public List<Config> getAll() {
        return repo.findAll();
    }
}
