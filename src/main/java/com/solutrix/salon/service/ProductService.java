package com.solutrix.salon.service;

import com.solutrix.salon.entity.Product;
import com.solutrix.salon.exception.ResourceNotFoundException;
import com.solutrix.salon.repository.ProductRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Product> getAllProducts(int brhid) {
        return repo.findAllByBrhidIsAndAndStatusNot(brhid,7);
    }


    public Optional<Product> getProductById(int docno) {
        return repo.findById(docno);
    }

    @Transactional
    public Product createProduct(Product product) {
        entityManager.clear();
        product.setStatus(3);
        Optional<Integer> maxDocNo = repo.findMaxDocNo();
        Optional<Integer> maxVocNo = repo.findMaxVocNo(product.getBrhid());
        product.setDocno(maxDocNo.orElse(0) + 1);
        product.setDate(Date.valueOf(LocalDate.now()));
        product.setVocno(maxVocNo.orElse(0) + 1);
        return repo.save(product);
    }

    @Transactional
    public Product updateProduct(int id, Product product) {
        Product productitem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));
        productitem.setRefname(product.getRefname());
        productitem.setAmount(product.getAmount());
        return repo.save(productitem);
    }

    @Transactional
    public void deleteProduct(int id) {
        /*User useritem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        useritem.setStatus(7);*/
        repo.deleteById(id);
    }

}
