package com.example.datajpa.repository;

import com.example.datajpa.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findAccountByActNo(String actNo);

    Optional<List<Account>> findAccountByCustomer_Phone(String phoneNumber);

    boolean existsByCustomer_PhoneAndAccountType_TypeName(String phone, String typeName);

    boolean existsByActNo(String s);
}