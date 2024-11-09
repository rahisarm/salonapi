package com.solutrix.salon.controller;

import com.solutrix.salon.dto.ConfigDTO;
import com.solutrix.salon.entity.Config;
import com.solutrix.salon.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @PutMapping
    public ResponseEntity<List<Config>> updateConfigs(@RequestBody List<Config> configs) {
        System.out.println(configs);
        List<Config> updatedConfigs = configService.updateConfigs(configs);
        return ResponseEntity.ok(updatedConfigs);
    }
}
