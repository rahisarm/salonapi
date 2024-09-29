package com.solutrix.salon.controller;

import com.solutrix.salon.dto.UserDTO;
import com.solutrix.salon.entity.User;
import com.solutrix.salon.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/all/{brhid}")
    public List<UserDTO> getAllUsers(@PathVariable int brhid) {
        System.out.println(service.getAllUsers(brhid));
        return service.getAllUsers(brhid);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return service.createUser(user);
    }

    @GetMapping("/{docno}")
    public ResponseEntity<User> getUserById(@PathVariable int docno) {
        Optional<User> branch = service.getUserById(docno);
        return branch.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{docno}")
    public ResponseEntity deleteUser(@PathVariable int docno) {
        try {
            service.deleteUser(docno);
            return ResponseEntity.ok(true);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User updatedUser) {
        try {
            User updated = service.updateUser(updatedUser.getDocno(), updatedUser);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
