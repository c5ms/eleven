package com.motiveschina.erp.application.convertor;

import com.motiveschina.erp.application.model.InventoryDto;
import com.motiveschina.erp.domain.inventory.Inventory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryConvertor {

    private final ModelMapper modelMapper;

    public InventoryDto toDto(Inventory inventory) {
        return modelMapper.map(inventory, InventoryDto.class);
    }
}
