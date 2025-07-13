package com.example.datajpa.repository;

import com.example.datajpa.domain.KYC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KYCRepository extends JpaRepository<KYC,String> {

}
