package com.solutrix.salon.service;

import com.solutrix.salon.dto.DashboardDTO;
import com.solutrix.salon.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

@Service
public class DashboardService {


    @Autowired
    private InvoiceMasterRepo invoiceMasterRepo;

    @Autowired
    private ExpenseRepo expenseRepo;

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    public DashboardDTO getDashboardData(DashboardDTO dto) {
        DashboardDTO dashboardDTO = new DashboardDTO();
        //System.out.println(dto);

        Double totalinvoice= 0.0;
        Double totalexpense= 0.0;
        Integer activeEmployees= 0;
        Integer newClients= 0;
        List<Object[]> incomeResults=new ArrayList<Object[]>();
        List<Object[]> expenseResults=new ArrayList<Object[]>();

        LocalDate start = dto.getFromdate().toLocalDate();
        LocalDate end = dto.getTodate().toLocalDate();

        if(dto.getReportbrhid()!=null && dto.getReportbrhid()>0){
            totalinvoice=invoiceMasterRepo.findSumSubtotal(dto.getFromdate(),dto.getTodate(),dto.getReportbrhid());
            activeEmployees=employeeRepo.countActiveByBrhid(dto.getBrhid());
            totalexpense=expenseRepo.findExpense(dto.getFromdate(),dto.getTodate(),dto.getReportbrhid());
            incomeResults = invoiceMasterRepo.findIncomeByMonth(dto.getFromdate(), dto.getTodate(), dto.getReportbrhid());
            expenseResults = expenseRepo.findExpenseByMonth(dto.getFromdate(), dto.getTodate(), dto.getReportbrhid());
        }
        else {
            totalinvoice=invoiceMasterRepo.findSumSubtotalAllBranch(dto.getFromdate(),dto.getTodate());
            activeEmployees=employeeRepo.countActiveByBrhid(dto.getBrhid());
            totalexpense=expenseRepo.findExpenseAllBranch(dto.getFromdate(),dto.getTodate());
            incomeResults = invoiceMasterRepo.findIncomeByMonthAllBranch(dto.getFromdate(), dto.getTodate());
            expenseResults = expenseRepo.findExpenseByMonthAllBranch(dto.getFromdate(), dto.getTodate());
        }

        List<Map<String,Object>> allmonths=generateAllMonths(dto.getFromdate(),dto.getTodate());
        List<Map<String,Object>> incomeexpensechartdata=new ArrayList<>();
        allmonths.forEach(monthitem->{
            int year=Integer.parseInt(monthitem.get("year").toString());
            int month=Integer.parseInt(monthitem.get("month").toString());

            Double income=0.0,expense=0.0;
            if(dto.getReportbrhid()!=null && dto.getReportbrhid()>0){
                income=invoiceMasterRepo.findSumTaxTotal(month,year,dto.getReportbrhid());
                expense=expenseRepo.findSumTaxTotal(month,year,dto.getReportbrhid());
            }
            else{
                income=invoiceMasterRepo.findSumTaxTotal(month,year);
                expense=expenseRepo.findSumTaxTotal(month,year);
            }

            Map<String,Object> map=new HashMap<>();
            map.put("income",income);
            map.put("expense",expense);
            map.put("month",monthitem.get("monthname").toString());
            incomeexpensechartdata.add(map);
        });

        System.out.println("Branch:"+dto.getReportbrhid());
        dashboardDTO.setExpAccountChart(expenseRepo.findExpenseByAccount(dto.getFromdate(),dto.getTodate(),dto.getReportbrhid()));
        dashboardDTO.setExpTypeChart(expenseRepo.findExpenseByAccount(dto.getFromdate(),dto.getTodate(),dto.getReportbrhid()));

        dashboardDTO.setIncomeexpensechart(incomeexpensechartdata);
        dashboardDTO.setTotalInvoice(totalinvoice);
        dashboardDTO.setTotalExpense(totalexpense);
        dashboardDTO.setActiveEmployees(activeEmployees);
        dashboardDTO.setNewCustomers(newClients);
        
        System.out.println(dashboardDTO);
        return dashboardDTO;
    }

    private List<Map<String, Object>> generateAllMonths(Date fromDate, Date toDate) {
        List<Map<String, Object>> monthsList = new ArrayList<>();

        Calendar start = Calendar.getInstance();
        start.setTime(fromDate);

        Calendar end = Calendar.getInstance();
        end.setTime(toDate);

        end.add(Calendar.DAY_OF_MONTH, 1);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-yy", Locale.ENGLISH);
        //while (start.before(end)/* || start.equals(end)*/) {
        while (start.get(Calendar.YEAR) < end.get(Calendar.YEAR) ||
                (start.get(Calendar.YEAR) == end.get(Calendar.YEAR) && start.get(Calendar.MONTH) <= end.get(Calendar.MONTH))) {

            String monthKey = String.format("%04d-%02d", start.get(Calendar.YEAR), start.get(Calendar.MONTH) + 1);
            Map<String, Object> monthData = new HashMap<>();
            monthData.put("monthname", dateFormat.format(start.getTime()));
            monthData.put("monthkey", monthKey);
            monthData.put("year", start.get(Calendar.YEAR));
            monthData.put("month", start.get(Calendar.MONTH) + 1);
            monthData.put("income", 0.0); // Default income
            monthData.put("expense", 0.0); // Default expense
            monthsList.add(monthData);
            start.add(Calendar.MONTH, 1);
        }

        return monthsList;
    }

    public DashboardDTO getDailyCounterData(DashboardDTO dashboardDTO) {
        DashboardDTO dailyCounterData = new DashboardDTO();

        DashboardDTO invdailydata=invoiceMasterRepo.findDailyInvCounter(dashboardDTO.getDailydate());
        DashboardDTO expdailydata=expenseRepo.findDailyExpCounter(dashboardDTO.getDailydate());

        dailyCounterData.setDailyexpcard(expdailydata.getDailyexpcard());
        dailyCounterData.setDailyexpcash(expdailydata.getDailyexpcash());
        dailyCounterData.setDailyexpcredit(expdailydata.getDailyexpcredit());
        dailyCounterData.setDailyexptotal(dailyCounterData.getDailyexpcash()+expdailydata.getDailyexpcard());

        dailyCounterData.setDailyinvcard(invdailydata.getDailyinvcard());
        dailyCounterData.setDailyinvcash(invdailydata.getDailyinvcash());
        dailyCounterData.setDailyinvcredit(invdailydata.getDailyinvcredit());
        dailyCounterData.setDailyinvtotal(dailyCounterData.getDailyinvcash()+dailyCounterData.getDailyinvcard());

        return dailyCounterData;
    }
}