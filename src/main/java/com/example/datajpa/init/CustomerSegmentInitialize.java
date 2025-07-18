package com.example.datajpa.init;

import com.example.datajpa.domain.CustomerSegment;
import com.example.datajpa.repository.CustomerSegmentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerSegmentInitialize {

    private final CustomerSegmentRepository customerSegmentRepository;

    @PostConstruct
    public void init(){
        if(customerSegmentRepository.count() == 0) {
            CustomerSegment regular = new CustomerSegment();
            regular.setSegmentName("REGULAR");
            regular.setDescription("Regular Customer");
            regular.setIsDeleted(false);

            CustomerSegment silver = new CustomerSegment();
            silver.setSegmentName("SILVER");
            silver.setDescription("Silver Customer");
            silver.setIsDeleted(false);

            CustomerSegment gold = new CustomerSegment();
            gold.setSegmentName("GOLD");
            gold.setDescription("Gold Customer");
            gold.setIsDeleted(false);

            customerSegmentRepository.saveAll(
                    List.of(regular, silver, gold)
            );
        }
    }
}
