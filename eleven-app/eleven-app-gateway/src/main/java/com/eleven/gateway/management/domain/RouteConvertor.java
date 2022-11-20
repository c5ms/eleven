package com.eleven.gateway.management.domain;

import com.eleven.gateway.management.model.RouteDto;
import com.eleven.core.data.support.ListValue;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RouteConvertor {

    private final ModelMapper modelMapper;



    public RouteDto toDto(Route route) {
        var dto = modelMapper.map(route, RouteDto.class);
//        dto.setFilters(route.getFilters().getValues());
        // process properties
        return dto;
    }
}
