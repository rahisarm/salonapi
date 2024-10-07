package com.solutrix.salon.component;

import com.solutrix.salon.repository.AccountRepo;
import com.solutrix.salon.repository.ConfigRepo;
import com.solutrix.salon.repository.PayTypeRepo;
import com.solutrix.salon.repository.VendorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Common {

    @Autowired
    private ConfigRepo configRepo;

    @Autowired
    private VendorRepo vendorRepo;

    @Autowired
    private PayTypeRepo payTypeRepo;

    @Autowired
    private AccountRepo accountRepo;

    public String getAccountNumberByPayType(int payType, int vendorId) {
        String payTypeRefname=payTypeRepo.findById(payType).get().getRefname();
        switch (payTypeRefname) {
            case "Cash":
                return configRepo.findByFieldname("CashAcno").get().getConfigvalue();
            case "Credit":
                return vendorRepo.findById(vendorId).get().getAcno()+"";
            default:
                return configRepo.findByFieldname("BankAcno").get().getConfigvalue();
        }
    }

    public int getTaxAcno(){
        return Integer.parseInt(configRepo.findByFieldname("TaxAcno").get().getConfigvalue());
    }

}
