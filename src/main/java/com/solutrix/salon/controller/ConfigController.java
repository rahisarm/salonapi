package com.solutrix.salon.controller;

import com.solutrix.salon.entity.Config;
import com.solutrix.salon.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/config")
public class ConfigController {
    @Autowired
    private ConfigService configService;

    @GetMapping
    public List<Config> getAllConfigs() {
        return configService.getAll();
    }
}
