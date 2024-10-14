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
import java.util.ArrayList;
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
                "Combo",
                comboDetailDTOList
        );
    }

    // Helper method to map ComboDetail to ComboDetailDTO and include product info
    private ComboDetailDTO mapToComboDetailDTO(ComboDetail comboDetail) {
        Optional<Product> product=productRepo.findById(comboDetail.getPsrno());
        return new ComboDetailDTO(
                comboDetail.getDocno(),
                comboDetail.getComboMaster().getDocno(),
                product.get().getDocno(),
                product.get().getRefname(),    // product name
                product.get().getAmount()      // product amount
        );
    }


    public Optional<ComboMasterDTO> getComboMasterById(int docno) {
        ComboMasterDTO masterDTO=new ComboMasterDTO();
        Optional<ComboMaster> comboMaster=comboMasterRepo.findById(docno);
        if(comboMaster.isPresent()) {
            masterDTO.setDocno(comboMaster.get().getDocno());
            masterDTO.setBrhid(comboMaster.get().getBrhid());
            masterDTO.setStatus(comboMaster.get().getStatus());
            masterDTO.setUserid(comboMaster.get().getUserid());
            masterDTO.setFromdate(comboMaster.get().getFromdate());
            masterDTO.setTodate(comboMaster.get().getTodate());
            masterDTO.setAmount(comboMaster.get().getAmount());
            masterDTO.setDescription(comboMaster.get().getDescription());
            masterDTO.setRefname(comboMaster.get().getRefname());
            List<ComboDetailDTO> comboDetailDTOList = comboMaster.get().getComboDetailList().stream()
                    .map(this::mapToComboDetailDTO)
                    .collect(Collectors.toList());
            masterDTO.setComboDetailList(comboDetailDTOList);
            return Optional.of(masterDTO);
        }
        else{
            return Optional.empty();
        }
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
    public ComboMaster updateComboMaster(ComboMasterDTO comboMasterDTO) {
        ComboMaster comboMasteritem=masterRepo.findById(comboMasterDTO.getDocno()).orElseThrow(()-> new ResourceNotFoundException("ComboMaster Not Found"));
        comboMasteritem.setAmount(comboMasterDTO.getAmount());
        comboMasteritem.setDescription(comboMasterDTO.getDescription());
        comboMasteritem.setRefname(comboMasterDTO.getRefname());

        List<ComboDetail> details = comboMasterDTO.getComboDetailList().stream()
                .map(detailDTO -> {
                    ComboDetail detail = new ComboDetail();
                    detail.setPsrno(detailDTO.getPsrno());
                    detail.setComboMaster(comboMasteritem);
                    return detail;
                }).collect(Collectors.toList());
        comboMasteritem.setComboDetailList(details);
        return masterRepo.save(comboMasteritem);
    }

    @Transactional
    public void deleteComboMaster(int id) {
        /*User useritem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        useritem.setStatus(7);*/
        comboMasterRepo.deleteById(id);
    }

}
