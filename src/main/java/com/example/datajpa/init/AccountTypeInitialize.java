package com.example.datajpa.init;


import com.example.datajpa.domain.AccountType;
import com.example.datajpa.repository.AccountTypeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountTypeInitialize {
    private final AccountTypeRepository accountTypeRepository;

    @PostConstruct
    public void init() {
        if (accountTypeRepository.count() == 0) {
            AccountType payroll = new AccountType();
            payroll.setTypeName("PAYROLL");
            payroll.setIsDeleted(false);

            AccountType saving = new AccountType();
            saving.setTypeName("SAVING");
            saving.setIsDeleted(false);

            AccountType junior = new AccountType();
            junior.setTypeName("JUNIOR");
            junior.setIsDeleted(false);

            accountTypeRepository.saveAll(
                    List.of(payroll, saving, junior)
            );

        }
    }
}
