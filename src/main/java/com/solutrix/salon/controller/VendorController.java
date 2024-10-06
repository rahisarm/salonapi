package com.solutrix.salon.controller;

import com.solutrix.salon.entity.Vendor;
import com.solutrix.salon.service.VendorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {

    @Autowired
    private VendorService service;

    @GetMapping("/all/{brhid}")
    public List<Vendor> getAllVendors(@PathVariable int brhid) {
        return service.getAllVendors(brhid);
    }

    @PostMapping
    public Vendor createVendor(@RequestBody Vendor vendor) {
        return service.createVendor(vendor);
    }

    @GetMapping("/{docno}")
    public ResponseEntity<Vendor> getVendorById(@PathVariable int docno) {
        Optional<Vendor> vendor = service.getVendorById(docno);
        return vendor.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{docno}")
    public ResponseEntity deleteVendor(@PathVariable int docno) {
        try {
            service.deleteVendor(docno);
            return ResponseEntity.ok(true);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<Vendor> updateVendor(@RequestBody Vendor updatedVendor) {
        try {
            Vendor updated = service.updateVendor(updatedVendor.getDocno(), updatedVendor);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
