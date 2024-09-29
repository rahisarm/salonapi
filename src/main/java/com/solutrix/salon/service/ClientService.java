package com.solutrix.salon.service;

import com.solutrix.salon.dto.UserDTO;
import com.solutrix.salon.entity.Client;
import com.solutrix.salon.entity.User;
import com.solutrix.salon.exception.ResourceNotFoundException;
import com.solutrix.salon.repository.ClientRepo;
import com.solutrix.salon.repository.UserRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepo repo;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Client> getAllClients(int brhid) {
        return repo.findAllByBrhidIsAndAndStatusNot(brhid,7);
    }


    public Optional<Client> getClientById(int docno) {
        return repo.findById(docno);
    }

    @Transactional
    public Client createClient(Client client) {
        entityManager.clear();
        client.setStatus(3);
        Optional<Integer> maxDocNo = repo.findMaxDocNo();
        Optional<Integer> maxVocNo = repo.findMaxVocNo(client.getBrhid());
        client.setDocno(maxDocNo.orElse(0) + 1);
        client.setDate(Date.valueOf(LocalDate.now()));
        client.setVocno(maxVocNo.orElse(0) + 1);
        return repo.save(client);
    }

    @Transactional
    public Client updateClient(int id, Client client) {
        Client clientitem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Client Not Found"));
        clientitem.setRefname(client.getRefname());
        clientitem.setMobile(client.getMobile());
        clientitem.setEmail(client.getEmail());
        return repo.save(clientitem);
    }

    @Transactional
    public void deleteClient(int id) {
        /*User useritem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        useritem.setStatus(7);*/
        repo.deleteById(id);
    }

}
