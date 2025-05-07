package com.motiveschina.erp.application.convertor.support;

import java.util.Optional;
import com.motiveschina.erp.application.model.MaterialDto;
import com.motiveschina.erp.domain.material.MaterialFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MaterialIdToMaterialDtoConvertor implements Converter<Long, MaterialDto> {

    private final MaterialFinder materialFinder;
    private final ModelMapper modelMapper;

    @Override
    public MaterialDto convert(MappingContext<Long, MaterialDto> context) {
        return Optional.ofNullable(context.getSource())
            .flatMap(materialFinder::get)
            .map(material -> modelMapper.map(material, MaterialDto.class))
            .orElse(null);
    }

}
