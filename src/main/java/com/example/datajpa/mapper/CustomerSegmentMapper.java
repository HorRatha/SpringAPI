package com.example.datajpa.mapper;

import com.example.datajpa.domain.CustomerSegment;
import com.example.datajpa.dto.CreateCustomerSegmentRequest;
import com.example.datajpa.dto.CustomerSegmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerSegmentMapper {

    @Mapping(target = "segmentName", expression = "java(createCustomerSegmentRequest.segmentName().toUpperCase())")
    CustomerSegment customerSegmentRequestToCustomerSegment(CreateCustomerSegmentRequest createCustomerSegmentRequest);

    CustomerSegmentResponse toCustomerSegmentResponse(CustomerSegment customerSegment);

}
