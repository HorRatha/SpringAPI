package com.example.datajpa.repository;

import com.example.datajpa.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findAccountByActNo(String actNo);

    // Changed from Customer_Phone to Customer_PhoneNumber
    Optional<List<Account>> findAccountByCustomer_PhoneNumber(String phoneNumber);

    // Changed from Customer_Phone to Customer_PhoneNumber
    boolean existsByCustomer_PhoneNumberAndAccountType_TypeName(String phoneNumber, String typeName);

}