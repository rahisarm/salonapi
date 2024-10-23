package com.solutrix.salon.controller;

import com.solutrix.salon.dto.DashboardDTO;
import com.solutrix.salon.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardDTO> getDashboardData(@RequestBody DashboardDTO dto) {
        DashboardDTO dashboardData = dashboardService.getDashboardData(dto);
        return ResponseEntity.ok(dashboardData);
    }
}
