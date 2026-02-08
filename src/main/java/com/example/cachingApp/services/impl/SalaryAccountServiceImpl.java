package com.example.cachingApp.services.impl;

import com.example.cachingApp.entities.Employee;
import com.example.cachingApp.entities.SalaryAccount;
import com.example.cachingApp.repositories.SalaryAccountRepository;
import com.example.cachingApp.services.SalaryAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)  // Required is default
public class SalaryAccountServiceImpl implements SalaryAccountService {

    private final SalaryAccountRepository salaryAccountRepository;
    @Override
    public void createAccount(Employee employee) {

        if(employee.getName().equals("Sarthak")) throw new RuntimeException("Sarthak is now allowed");
        SalaryAccount salaryAccount = SalaryAccount.builder()
                .employee(employee)
                .amount(BigDecimal.ZERO)
                .build();

        salaryAccountRepository.save(salaryAccount);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)  //all operations will happen like they were happening sequentiallly
    public SalaryAccount incrementAmount(Long accountId) {
        SalaryAccount salaryAccount = salaryAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id " +accountId));

        BigDecimal prevAmt = salaryAccount.getAmount();
        BigDecimal newAmt = prevAmt.add(BigDecimal.TEN);
        salaryAccount.setAmount(newAmt);

        return salaryAccountRepository.save(salaryAccount);
    }
}
