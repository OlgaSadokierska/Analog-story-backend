package com.olgasadokierska.analogstory.user.controller;

import com.olgasadokierska.analogstory.user.model.AccountType;
import com.olgasadokierska.analogstory.user.service.AccountTypeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account-types")
public class AccountTypeController {

    @Autowired
    private AccountTypeService accountTypeService;

    @PostMapping
    @Transactional
    public AccountType createAccountType(@RequestBody AccountType accountType) {

        return accountTypeService.createAccountType(accountType);
    }
}

