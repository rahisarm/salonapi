package com.solutrix.salon.controller;

import com.solutrix.salon.dto.ProductDTO;
import com.solutrix.salon.dto.ServiceComboDTO;
import com.solutrix.salon.entity.Product;
import com.solutrix.salon.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/servicecombo/{brhid}")
    public List<ServiceComboDTO> getAllServiceCombo(@PathVariable Integer brhid) {
        return service.getAllServiceCombo(brhid);
    }
    @GetMapping("/all/{brhid}")
    public List<ProductDTO> getAllProducts(@PathVariable int brhid) {
        return service.getAllProducts(brhid);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return service.createProduct(product);
    }

    @GetMapping("/{docno}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable int docno) {
        Optional<ProductDTO> product = service.getProductById(docno);
        return product.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{docno}")
    public ResponseEntity deleteProduct(@PathVariable int docno) {
        try {
            service.deleteProduct(docno);
            return ResponseEntity.ok(true);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product updatedProduct) {
        try {
            Product updated = service.updateProduct(updatedProduct.getDocno(), updatedProduct);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
