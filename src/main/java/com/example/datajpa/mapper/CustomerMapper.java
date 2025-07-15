package com.example.datajpa.mapper;



import com.example.datajpa.domain.Customer;
import com.example.datajpa.dto.CustomerRequest;
import com.example.datajpa.dto.CustomerResponse;
import com.example.datajpa.dto.UpdateCustomerRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "customerSegment", ignore = true)
    @Mapping(target = "kyc", ignore = true)
    Customer customerRequestToCustomer(CustomerRequest createCustomerRequest);

    CustomerResponse customerToCustomerResponse(Customer customer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toCustomerPartially(UpdateCustomerRequest updateCustomerRequest,@MappingTarget Customer customer);

}