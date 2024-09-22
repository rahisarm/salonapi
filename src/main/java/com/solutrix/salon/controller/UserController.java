package com.solutrix.salon.controller;

import com.solutrix.salon.entity.User;
import com.solutrix.salon.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/{brhid}")
    public List<User> getAllUsers(@PathVariable int brhid) {
        return service.getAllUsers(brhid);
    }

    @PostMapping
    public User createUser(@RequestBody User branch) {
        return service.createUser(branch);
    }

    @GetMapping("/{docno}")
    public ResponseEntity<User> getUserById(@PathVariable int docno) {
        Optional<User> branch = service.getUserById(docno);
        return branch.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{docno}")
    public ResponseEntity<User> deleteUser(@PathVariable int docno) {
        Optional<User> branch = service.getUserById(docno);
        service.deleteUser(docno);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{docno}")
    public ResponseEntity<User> updateUser(@PathVariable int docno, @RequestBody User updatedUser) {
        try {
            User updated = service.updateUser(docno, updatedUser);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
