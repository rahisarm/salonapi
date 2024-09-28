package com.solutrix.salon.controller;

import com.solutrix.salon.entity.Branch;
import com.solutrix.salon.entity.Userlevel;
import com.solutrix.salon.service.BranchService;
import com.solutrix.salon.service.UserlevelService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/userlevel")
public class UserlevelController {

    @Autowired
    private UserlevelService service;

    @GetMapping
    public List<Userlevel> getAllUserlevels() {
        return service.getAllUserlevels();
    }

}
