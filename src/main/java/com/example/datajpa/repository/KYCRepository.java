package com.example.datajpa.repository;

import com.example.datajpa.domain.KYC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KYCRepository extends JpaRepository<KYC, String> {

    boolean existsByNationalCodeId(String nationalCodeId);

    Optional<KYC> findKYCByNationalCodeId(String nationalCodeId);
}
