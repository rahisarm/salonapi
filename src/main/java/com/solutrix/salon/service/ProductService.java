package com.solutrix.salon.service;

import com.solutrix.salon.dto.ProductDTO;
import com.solutrix.salon.dto.ServiceComboDTO;
import com.solutrix.salon.entity.ComboMaster;
import com.solutrix.salon.entity.Product;
import com.solutrix.salon.exception.ResourceNotFoundException;
import com.solutrix.salon.repository.ComboMasterRepo;
import com.solutrix.salon.repository.ProductRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    @Autowired
    private ComboMasterRepo comboRepo;

    @PersistenceContext
    private EntityManager entityManager;

    public List<ProductDTO> getAllProducts(int brhid) {
        List<Product> productList=repo.findAllByBrhidIsAndAndStatusNot(brhid,7);
        List<ProductDTO> productDTOList=new ArrayList<>();
        productList.stream().forEach(product -> {
            ProductDTO productDTO=new ProductDTO();
            productDTO.setAmount(product.getAmount());
            productDTO.setRefname(product.getRefname());
            productDTO.setDocno(product.getDocno());
            productDTO.setDate(product.getDate());
            productDTO.setStatus(product.getStatus());
            productDTO.setBrhid(product.getBrhid());
            productDTO.setUserid(product.getUserid());
            productDTO.setVocno(product.getVocno());
            productDTO.setServicetype("Service");
            productDTOList.add(productDTO);
        });
        return productDTOList;
    }


    public Optional<ProductDTO> getProductById(int docno) {
        Optional<Product> product= repo.findById(docno);
        if(product.isPresent()) {
            ProductDTO productDTO=new ProductDTO();
            productDTO.setAmount(product.get().getAmount());
            productDTO.setRefname(product.get().getRefname());
            productDTO.setDocno(product.get().getDocno());
            productDTO.setDate(product.get().getDate());
            productDTO.setStatus(product.get().getStatus());
            productDTO.setBrhid(product.get().getBrhid());
            productDTO.setUserid(product.get().getUserid());
            productDTO.setVocno(product.get().getVocno());
            productDTO.setServicetype("Service");
            return Optional.of(productDTO);
        }
        return Optional.empty();
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

    public List<ServiceComboDTO> getAllServiceCombo(Integer brhid) {
        List<Product> productList=repo.findAllByBrhidIsAndAndStatusNot(brhid,7);
        List<ServiceComboDTO> serviceComboDTOList=new ArrayList<ServiceComboDTO>();
        for(Product product:productList) {
            ServiceComboDTO serviceComboDTO=new ServiceComboDTO();
            serviceComboDTO.setDocno(product.getDocno());
            serviceComboDTO.setRefname(product.getRefname());
            serviceComboDTO.setAmount(product.getAmount());
            serviceComboDTO.setServicetype("Service");
            serviceComboDTOList.add(serviceComboDTO);
        }

        List<ComboMaster> comboMasterList=comboRepo.findAllByBrhidIsAndAndStatusNot(brhid,7);
        for(ComboMaster comboMaster:comboMasterList) {
            ServiceComboDTO serviceComboDTO=new ServiceComboDTO();
            serviceComboDTO.setDocno(comboMaster.getDocno());
            serviceComboDTO.setRefname(comboMaster.getRefname());
            serviceComboDTO.setAmount(comboMaster.getAmount());
            serviceComboDTO.setServicetype("Combo");
            serviceComboDTOList.add(serviceComboDTO);
        }
        return serviceComboDTOList;
    }
}
