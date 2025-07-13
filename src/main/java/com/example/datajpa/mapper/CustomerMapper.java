package com.example.datajpa.mapper;



import com.example.datajpa.domain.Customer;
import com.example.datajpa.dto.CustomerRequest;
import com.example.datajpa.dto.CustomerResponse;
import com.example.datajpa.dto.UpdateCustomerRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
            void toCustomerPartially(
            UpdateCustomerRequest updateCustomerRequest,
            @MappingTarget Customer customer
    );

//    Model(source) -> DTO(response)
//    What is source data? (parameter) (customer)
//    What is target data? (return_type) (response)

    CustomerResponse mapCustomerToCustomerResponse(Customer customer);
    Customer fromCreateCustomerRequest(CustomerRequest customerRequest);
    Customer fromUpdateCustomerRequest(CustomerRequest customerRequest);

}