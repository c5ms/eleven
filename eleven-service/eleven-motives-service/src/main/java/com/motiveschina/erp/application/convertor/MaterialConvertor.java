package com.motiveschina.erp.application.convertor;

import com.motiveschina.erp.application.model.MaterialDto;
import com.motiveschina.erp.domain.material.Material;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MaterialConvertor {

    private final ModelMapper modelMapper;


    public MaterialDto toDto(Material material) {
        return modelMapper.map(material,MaterialDto.class);
    }
}
