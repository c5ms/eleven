package com.motiveschina.erp.application.convertor;

import com.motiveschina.erp.application.dto.PurchaseOrderDto;
import com.motiveschina.erp.application.dto.PurchaseOrderItemDto;
import com.motiveschina.erp.domain.purchase.PurchaseOrder;
import com.motiveschina.erp.domain.purchase.PurchaseOrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PurchaseConvertor {

    private final ModelMapper modelMapper;

    public PurchaseOrderDto toDto(PurchaseOrder order) {
        return modelMapper.map(order, PurchaseOrderDto.class)
            .setItems(order.getItems().toList(this::toDto));
    }

    private PurchaseOrderItemDto toDto(PurchaseOrderItem orderItem) {
        return modelMapper.map(orderItem, PurchaseOrderItemDto.class);
    }

    public PurchaseOrderItem toDomain(PurchaseOrderItemDto item) {
        return modelMapper.map(item, PurchaseOrderItem.class);
    }

    public List<PurchaseOrderItem> toDomain(Collection<PurchaseOrderItemDto> items) {
        return  items
            .stream()
            .map(this::toDomain)
            .toList();
    }


}
