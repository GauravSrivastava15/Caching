package com.example.cachingApp.services;

import com.example.cachingApp.entities.Employee;
import com.example.cachingApp.entities.SalaryAccount;

public interface SalaryAccountService {
    void createAccount(Employee employee);

    SalaryAccount incrementAmount(Long accountId);
}
