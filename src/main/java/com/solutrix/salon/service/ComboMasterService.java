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
        List<ComboMasterDTO> masterDTOList = new ArrayList<>();
        comboMasters.forEach(comboMaster -> {
            ComboMasterDTO comboMasterDTO = new ComboMasterDTO();
            comboMasterDTO.setAmount(comboMaster.getAmount());
            comboMasterDTO.setDescription(comboMaster.getDescription());
            comboMasterDTO.setDate(comboMaster.getDate());
            comboMasterDTO.setFromdate(comboMaster.getFromdate());
            comboMasterDTO.setTodate(comboMaster.getTodate());
            comboMasterDTO.setBrhid(comboMaster.getBrhid());
            comboMasterDTO.setRefname(comboMaster.getRefname());
            comboMasterDTO.setStatus(comboMaster.getStatus());
            comboMasterDTO.setDocno(comboMaster.getDocno());
            comboMasterDTO.setServicetype("Combo");
            comboMasterDTO.setUserid(comboMaster.getUserid());
            comboMasterDTO.setVocno(comboMaster.getVocno());

            List<ComboDetailDTO> detailDTOList = new ArrayList<>();
            comboMaster.getComboDetailList().forEach(detailDTO -> {
                ComboDetailDTO comboDetailDTO = new ComboDetailDTO();
                Optional<Product> product=productRepo.findById(detailDTO.getPsrno());
                if(product.isPresent()) {
                    comboDetailDTO.setAmount(product.get().getAmount());
                    comboDetailDTO.setRefname(product.get().getRefname());
                }
                comboDetailDTO.setPsrno(detailDTO.getPsrno());
                comboDetailDTO.setRdocno(comboMaster.getDocno());
                comboDetailDTO.setDocno(detailDTO.getDocno());
                detailDTOList.add(comboDetailDTO);
            });
            comboMasterDTO.setComboDetailList(detailDTOList);
            masterDTOList.add(comboMasterDTO);
        });
        return masterDTOList;
        //return comboMasters.stream().map(this::mapToComboMasterDTO).collect(Collectors.toList());
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
            List<ComboDetailDTO> detailDTOList = new ArrayList<>();
            comboMaster.get().getComboDetailList().forEach(detailDTO -> {
                ComboDetailDTO comboDetailDTO = new ComboDetailDTO();
                Optional<Product> product=productRepo.findById(detailDTO.getPsrno());
                if(product.isPresent()) {
                    comboDetailDTO.setAmount(product.get().getAmount());
                    comboDetailDTO.setRefname(product.get().getRefname());
                }
                comboDetailDTO.setPsrno(detailDTO.getPsrno());
                comboDetailDTO.setRdocno(comboMaster.get().getDocno());
                comboDetailDTO.setDocno(detailDTO.getDocno());
                detailDTOList.add(comboDetailDTO);
            });
            masterDTO.setComboDetailList(detailDTOList);
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
        comboMaster.setUserid(dto.getUserid());
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

        detailRepo.deleteAllByComboMaster(comboMasteritem);

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
        Optional<ComboMaster> comboMaster=comboMasterRepo.findById(id);
        detailRepo.deleteAllByComboMaster(comboMaster.get());
        comboMasterRepo.deleteById(id);
    }

}
