package com.solutrix.salon.controller;

import com.solutrix.salon.dto.BranchDTO;
import com.solutrix.salon.dto.MenuDTO;
import com.solutrix.salon.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping
    public ResponseEntity<List<MenuDTO>> getAllMenu() {
        List<MenuDTO> menus=menuService.getAllMenu();
        return ResponseEntity.ok(menus);
    }
}
