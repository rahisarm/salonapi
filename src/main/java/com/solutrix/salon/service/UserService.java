package com.solutrix.salon.service;

import com.solutrix.salon.entity.User;
import com.solutrix.salon.exception.ResourceNotFoundException;
import com.solutrix.salon.repository.UserRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private PasswordEncoder  passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> getAllUsers(int brhid) {
        return repo.findAllActiveUsersByBranch(brhid);
    }

    public Optional<User> getUserById(int docno) {
        return repo.findById(docno);
    }

    @Transactional
    public User createUser(User user) {
        entityManager.clear();
        user.setStatus(3);
        Optional<Integer> maxDocNo = repo.findMaxDocNo();
        Optional<Integer> maxVocNo = repo.findMaxVocNo(user.getBrhid());
        user.setDocno(maxDocNo.orElse(0) + 1);
        user.setDate(Date.valueOf(LocalDate.now()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVocno(maxDocNo.orElse(0) + 1);
        return repo.save(user);
    }
    public User updateUser(int id, User user) {
        User useritem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        useritem.setUsername(user.getUsername());
        useritem.setMobile(user.getMobile());
        useritem.setEmail(user.getEmail());
        useritem.setFullname(user.getFullname());
        useritem.setPassword(user.getPassword());
        useritem.setRoleid(user.getRoleid());
        return repo.save(useritem);
    }

    public void deleteUser(int id) {
        User useritem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        useritem.setStatus(7);
        repo.save(useritem);
    }

}
