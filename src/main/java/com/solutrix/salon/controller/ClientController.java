package com.solutrix.salon.controller;

import com.solutrix.salon.entity.Client;
import com.solutrix.salon.service.ClientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ClientService service;

    @GetMapping("/all/{brhid}")
    public List<Client> getAllClients(@PathVariable int brhid) {
        return service.getAllClients(brhid);
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return service.createClient(client);
    }

    @GetMapping("/{docno}")
    public ResponseEntity<Client> getClientById(@PathVariable int docno) {
        Optional<Client> branch = service.getClientById(docno);
        return branch.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{docno}")
    public ResponseEntity deleteClient(@PathVariable int docno) {
        try {
            service.deleteClient(docno);
            return ResponseEntity.ok(true);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<Client> updateClient(@RequestBody Client updatedClient) {
        try {
            Client updated = service.updateClient(updatedClient.getDocno(), updatedClient);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
