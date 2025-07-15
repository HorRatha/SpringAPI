package com.example.datajpa.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(unique = true, nullable = false, length = 15)
    private String phone;

    private String gender;

    private Boolean isDeleted;

    @Column(columnDefinition = "TEXT")
    private String remark;

    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;

    @OneToOne(mappedBy = "customer")
    private KYC kyc;

    @ManyToOne(optional = false)
    private CustomerSegment customerSegment;
}
