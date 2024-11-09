package com.solutrix.salon.controller;

import com.solutrix.salon.dto.DashboardDTO;
import com.solutrix.salon.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @PostMapping
    public ResponseEntity<DashboardDTO> getDashboardData(@RequestBody DashboardDTO dashboardDTO) {

        DashboardDTO dashboardData = dashboardService.getDashboardData(dashboardDTO);
        return ResponseEntity.ok(dashboardData);
    }
    @PostMapping("/DailyCounter")
    public ResponseEntity<DashboardDTO> getDailyCounter(@RequestBody DashboardDTO dashboardDTO) {
        DashboardDTO dashboardData = dashboardService.getDailyCounterData(dashboardDTO);
        return ResponseEntity.ok(dashboardData);
    }

    @PostMapping("/PayrollProcess")
    public ResponseEntity<DashboardDTO> getPayrollData(@RequestBody DashboardDTO dashboardDTO) {
        DashboardDTO dashboardData = dashboardService.getPayrollData(dashboardDTO);
        return ResponseEntity.ok(dashboardData);
    }
}
