package com.olgasadokierska.analogstory.user.service;

import com.olgasadokierska.analogstory.user.model.AccountType;
import com.olgasadokierska.analogstory.user.repository.AccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountTypeService {

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Transactional
    public AccountType createAccountType(AccountType accountType) {

        return accountTypeRepository.save(accountType);
    }



}
