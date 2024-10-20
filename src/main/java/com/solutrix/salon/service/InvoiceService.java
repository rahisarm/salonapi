package com.solutrix.salon.service;

import com.solutrix.salon.dto.InvoiceDetailDTO;
import com.solutrix.salon.dto.InvoiceMasterDTO;
import com.solutrix.salon.entity.*;
import com.solutrix.salon.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceMasterRepo invoiceMasterRepo;

    @Autowired
    private InvoiceDetailRepo invoiceDetailRepo;

    @Autowired
    private TrnoService trnoService;

    @Autowired
    private ConfigRepo configRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private JvtranRepo jvrepo;

    @Autowired
    private AuditService auditService;
    @Autowired
    private PayTypeRepo payTypeRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ComboMasterRepo comboMasterRepo;

    @Transactional
    public InvoiceMaster createInvoiceMaster(InvoiceMasterDTO invoiceMasterDTO) {
        InvoiceMaster invoiceMaster = new InvoiceMaster();
        List<Config> configList=configRepo.findAll();
        invoiceMaster.setStatus(3);
        int trno=trnoService.getNewTrno("INV");
        Optional<Integer> maxDocNo = invoiceMasterRepo.findMaxDocNo();
        Optional<Integer> maxVocNo = invoiceMasterRepo.findMaxVocNo(invoiceMasterDTO.getBrhid());
        invoiceMaster.setDocno(maxDocNo.orElse(0) + 1);
        invoiceMaster.setVocno(maxVocNo.orElse(0) + 1);
        invoiceMaster.setTrno(trno);
        invoiceMaster.setDate(invoiceMasterDTO.getDate());
        invoiceMaster.setAmount(invoiceMasterDTO.getTotal());
        invoiceMaster.setCldocno(invoiceMasterDTO.getCldocno());
        invoiceMaster.setDiscount(invoiceMasterDTO.getDiscount());
        invoiceMaster.setPaytype(invoiceMasterDTO.getPaytype());
        invoiceMaster.setBrhid(invoiceMasterDTO.getBrhid());
        invoiceMaster.setUserid(invoiceMasterDTO.getUserid());
        invoiceMaster.setRemarks(invoiceMasterDTO.getDescription());
        invoiceMaster.setTaxpercent(invoiceMasterDTO.getTaxpercent());
        invoiceMaster.setTaxamount(invoiceMasterDTO.getTax());
        invoiceMaster.setTaxtotal(invoiceMasterDTO.getNettotal());
        invoiceMaster.setEmpid(invoiceMasterDTO.getEmpid());
        invoiceMaster.setChknightbonus(invoiceMasterDTO.getChknightbonus() ?1:0);
        invoiceMaster.setChkworkbonus(invoiceMasterDTO.getChkworkbonus() ?1:0);

        Optional<Employee> objemployee=employeeRepo.findById(invoiceMaster.getEmpid());

        if(invoiceMaster.getChkworkbonus()>0){
            configList.forEach(config->{
                if(config.getFieldname().trim().equalsIgnoreCase("WorkBonusPercent")){
                    if(config.getMethod()>0 && Double.parseDouble(config.getConfigvalue())>0){
                        double extrapercent=Double.parseDouble(config.getConfigvalue());
                        double targetpay=objemployee.get().getTargetamt();
                        double extraamt=(invoiceMaster.getAmount()-targetpay)*(extrapercent/100);
                        BigDecimal bd=new BigDecimal(extraamt);
                        bd=bd.setScale(2,BigDecimal.ROUND_HALF_UP);
                        extraamt=bd.doubleValue();
                        invoiceMaster.setWorkbonus(extraamt);
                        return;
                    }
                }
            });
        }

        List<InvoiceDetail> details = invoiceMasterDTO.getDetails().stream()
                .map(detailDTO -> {
                    InvoiceDetail detail = new InvoiceDetail();
                    detail.setInvoiceMaster(invoiceMaster);
                    detail.setTrno(invoiceMaster.getTrno());
                    detail.setServicetype(detailDTO.getServicetype());
                    detail.setServiceid(detailDTO.getServiceid());
                    detail.setAmount(detailDTO.getAmount());
                    return detail;
                }).toList();
        invoiceMaster.setDetails(details);

        Jvtran clientEntry = new Jvtran();
        Optional<Client> objclient=clientRepo.findById(invoiceMaster.getCldocno());

        clientEntry.setTrno(trno);
        clientEntry.setAcno(objclient.get().getAcno()); // Account Number for Expenses
        clientEntry.setAmount(invoiceMaster.getTaxtotal());
        clientEntry.setId(1); // Set an appropriate ID for expense
        clientEntry.setNote("Entry of Inv #" + invoiceMaster.getDocno());
        clientEntry.setUserid(invoiceMaster.getUserid());
        clientEntry.setBrhid(invoiceMaster.getBrhid());
        clientEntry.setDate(invoiceMaster.getDate());
        clientEntry.setDocNo(invoiceMaster.getDocno());
        clientEntry.setDtype("INV");
        clientEntry.setOutAmount(0.0);
        jvrepo.save(clientEntry);

        Jvtran companyEntry = new Jvtran();
        Optional<Config> objconfig=configRepo.findByFieldname("INVacno");

        companyEntry.setTrno(trno);
        companyEntry.setAcno(Integer.parseInt(objconfig.get().getConfigvalue())); // Account Number for Vendors
        companyEntry.setAmount((invoiceMasterDTO.getTotal()-invoiceMasterDTO.getDiscount())*-1); // Negate the amount
        companyEntry.setId(-1); // Set an appropriate ID for vendor entry
        companyEntry.setNote("Revenue entry of Inv #" + invoiceMaster.getDocno());
        companyEntry.setUserid(invoiceMaster.getUserid());
        companyEntry.setBrhid(invoiceMaster.getBrhid());
        companyEntry.setDate(invoiceMaster.getDate());
        companyEntry.setDocNo(invoiceMaster.getDocno());
        companyEntry.setDtype("INV");
        companyEntry.setOutAmount(0.0);
        jvrepo.save(companyEntry);

        if(invoiceMaster.getTaxamount()>0.0){
            Jvtran taxEntry = new Jvtran();
            objconfig=configRepo.findByFieldname("TaxAcno");
            taxEntry.setTrno(trno);
            taxEntry.setAcno(Integer.parseInt(objconfig.get().getConfigvalue())); // Account Number for Vendors
            taxEntry.setAmount(invoiceMaster.getTaxamount()*-1); // Negate the amount
            taxEntry.setId(-1); // Set an appropriate ID for vendor entry
            taxEntry.setNote("Invoice Tax entry of Exp #" + invoiceMaster.getDocno());
            taxEntry.setUserid(invoiceMaster.getUserid());
            taxEntry.setBrhid(invoiceMaster.getBrhid());
            taxEntry.setDate(invoiceMaster.getDate());
            taxEntry.setDocNo(invoiceMaster.getDocno());
            taxEntry.setDtype("INV");
            taxEntry.setOutAmount(0.0);
            jvrepo.save(taxEntry);
        }

        Audit audit = new Audit();
        audit.setDtype("INV");
        audit.setUserid(invoiceMaster.getUserid());
        audit.setBrhid(invoiceMaster.getBrhid());
        audit.setDocno(invoiceMaster.getDocno());
        audit.setMode("A");
        auditService.createAudit(audit);

        return invoiceMasterRepo.save(invoiceMaster);
    }

    public List<InvoiceMasterDTO> getAllInvoices() {
        List<InvoiceMaster> invoices=invoiceMasterRepo.findAll();
        List<InvoiceMasterDTO> invoiceMasterDTOList=new ArrayList<>();

        invoices.forEach(invoice->{
            InvoiceMasterDTO dto=new InvoiceMasterDTO();
            dto.setBrhid(invoice.getBrhid());
            dto.setDocno(invoice.getDocno());
            dto.setDate(invoice.getDate());
            dto.setCldocno(invoice.getCldocno());
            dto.setChkworkbonus(invoice.getChkworkbonus() > 0);
            dto.setChknightbonus(invoice.getChknightbonus() > 0);
            dto.setEmpid(invoice.getEmpid());
            dto.setDiscount(invoice.getDiscount());
            dto.setDescription(invoice.getRemarks());
            dto.setNettotal(invoice.getTaxtotal());
            dto.setPaytype(invoice.getPaytype());
            dto.setTax(invoice.getTaxamount());
            dto.setTotal(invoice.getAmount());
            Optional<PayType> payType=payTypeRepo.findById(invoice.getPaytype());
            if(payType.isPresent()){
                dto.setPaytypename(payType.get().getRefname());
            }
            Optional<Client> client=clientRepo.findById(invoice.getCldocno());
            if(client.isPresent()){
                dto.setClientmobile(client.get().getMobile());
            }
            Optional<Employee> employee=employeeRepo.findById(invoice.getEmpid());
            if(employee.isPresent()){
                dto.setEmpname(employee.get().getRefname());
            }

            List<InvoiceDetailDTO> invoiceDetailDTOList=new ArrayList<>();
            invoice.getDetails().forEach(detail->{
                InvoiceDetailDTO detailDTO=new InvoiceDetailDTO();
                detailDTO.setAmount(detail.getAmount());
                detailDTO.setServiceid(detail.getServiceid());
                detailDTO.setServicetype(detail.getServicetype());
                if(detail.getServicetype().trim().equalsIgnoreCase("Service")){
                    Optional<Product> product=productRepo.findById(detail.getServiceid());
                    product.ifPresent(value -> detailDTO.setServicename(value.getRefname()));
                }
                else if(detail.getServicetype().trim().equalsIgnoreCase("Combo")){
                    Optional<ComboMaster> combo=comboMasterRepo.findById(detail.getServiceid());
                    combo.ifPresent(value -> detailDTO.setServicename(value.getRefname()));
                }
                invoiceDetailDTOList.add(detailDTO);

            });

            dto.setDetails(invoiceDetailDTOList);
            invoiceMasterDTOList.add(dto);
        });
        System.out.println(invoiceMasterDTOList);
        return invoiceMasterDTOList;
    }

    public Optional<InvoiceMaster> getInvoiceById(Integer id) {
        return invoiceMasterRepo.findById(id);
    }

    public void deleteInvoice(Integer id) {
        invoiceMasterRepo.deleteById(id);
    }


}
