package com.example.datajpa.repository;

import com.example.datajpa.domain.CustomerSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerSegmentRepository extends JpaRepository<CustomerSegment, Integer> {
    boolean existsBySegmentName(String segmentName);
    Optional<CustomerSegment> findBySegmentNameIgnoreCase(String segmentName);

}