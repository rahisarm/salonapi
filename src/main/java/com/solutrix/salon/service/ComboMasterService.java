package com.solutrix.salon.service;

import com.solutrix.salon.dto.ComboDetailDTO;
import com.solutrix.salon.dto.ComboMasterDTO;
import com.solutrix.salon.entity.ComboDetail;
import com.solutrix.salon.entity.ComboMaster;
import com.solutrix.salon.entity.Product;
import com.solutrix.salon.exception.ResourceNotFoundException;
import com.solutrix.salon.repository.ComboDetailRepo;
import com.solutrix.salon.repository.ComboMasterRepo;
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
import java.util.stream.Collectors;

@Service
public class ComboMasterService {

    @Autowired
    private ComboMasterRepo masterRepo;

    @Autowired
    private ComboDetailRepo detailRepo;

    @Autowired
    private ProductRepo productRepo;

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ComboMasterRepo comboMasterRepo;

    // Fetch and map to ComboMasterDTO
    public List<ComboMasterDTO> getAllComboMasters(int brhid) {
        List<ComboMaster> comboMasters = comboMasterRepo.findAllByBrhidIsAndAndStatusNot(brhid,7);
        return comboMasters.stream().map(this::mapToComboMasterDTO).collect(Collectors.toList());
    }

    // Helper method to map ComboMaster to ComboMasterDTO
    private ComboMasterDTO mapToComboMasterDTO(ComboMaster comboMaster) {
        List<ComboDetailDTO> comboDetailDTOList = comboMaster.getComboDetailList().stream()
                .map(this::mapToComboDetailDTO)
                .collect(Collectors.toList());

        return new ComboMasterDTO(
                comboMaster.getDocno(),
                comboMaster.getRefname(),
                comboMaster.getFromdate(),
                comboMaster.getTodate(),
                comboMaster.getAmount(),
                comboMaster.getDescription(),
                comboMaster.getStatus(),
                comboMaster.getUserid(),
                comboMaster.getBrhid(),
                comboMaster.getDate(),
                comboMaster.getVocno(),
                comboDetailDTOList
        );
    }

    // Helper method to map ComboDetail to ComboDetailDTO and include product info
    private ComboDetailDTO mapToComboDetailDTO(ComboDetail comboDetail) {
        Optional<Product> product=productRepo.findById(comboDetail.getPsrno());
        return new ComboDetailDTO(
                comboDetail.getComboMaster().getDocno(),
                product.get().getDocno(),
                product.get().getRefname(),    // product name
                product.get().getAmount()      // product amount
        );
    }


    public Optional<ComboMaster> getComboMasterById(int docno) {
        return masterRepo.findById(docno);
    }

    @Transactional
    public ComboMaster createComboMaster(ComboMasterDTO dto) {
        entityManager.clear();
        ComboMaster comboMaster = new ComboMaster();
        comboMaster.setStatus(3);
        Optional<Integer> maxDocNo = masterRepo.findMaxDocNo();
        Optional<Integer> maxVocNo = masterRepo.findMaxVocNo(comboMaster.getBrhid());
        comboMaster.setDocno(maxDocNo.orElse(0) + 1);
        comboMaster.setDate(Date.valueOf(LocalDate.now()));
        comboMaster.setVocno(maxVocNo.orElse(0) + 1);
        comboMaster.setBrhid(dto.getBrhid());
        comboMaster.setAmount(dto.getAmount());
        comboMaster.setDescription(dto.getDescription());
        comboMaster.setRefname(dto.getRefname());

        List<ComboDetail> details = dto.getComboDetailList().stream()
                .map(detailDTO -> {
                    ComboDetail detail = new ComboDetail();
                    detail.setPsrno(detailDTO.getPsrno());
                    detail.setComboMaster(comboMaster);
                    return detail;
                }).collect(Collectors.toList());
        comboMaster.setComboDetailList(details);

        return masterRepo.save(comboMaster);
    }

    @Transactional
    public ComboMaster updateComboMaster(int id, ComboMaster comboMaster) {
        ComboMaster comboMasteritem=masterRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("ComboMaster Not Found"));
        comboMasteritem.setDescription(comboMaster.getDescription());
        comboMasteritem.setAmount(comboMaster.getAmount());
        comboMasteritem.setRefname(comboMaster.getRefname());
        comboMasteritem.setFromdate(comboMaster.getFromdate());
        comboMasteritem.setTodate(comboMaster.getTodate());
        comboMasteritem.setComboDetailList(comboMaster.getComboDetailList());
        return masterRepo.save(comboMasteritem);
    }

    @Transactional
    public void deleteComboMaster(int id) {
        /*User useritem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        useritem.setStatus(7);*/
        comboMasterRepo.deleteById(id);
    }

}
