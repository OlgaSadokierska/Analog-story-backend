package com.olgasadokierska.analogstory.user.repository;

import com.olgasadokierska.analogstory.user.model.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
}
