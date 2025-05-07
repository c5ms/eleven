package com.motiveschina.erp.application.convertor;

import java.util.Optional;
import com.motiveschina.erp.application.model.MaterialDto;
import com.motiveschina.erp.application.model.PurchaseOrderDto;
import com.motiveschina.erp.domain.purchase.PurchaseOrder;
import com.motiveschina.erp.domain.purchase.PurchaseOrderItem;
import com.motiveschina.erp.domain.purchase.PurchaseOrderMaterial;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PurchaseOrderConvertor {

    private final ModelMapper modelMapper;

    public PurchaseOrderDto toDto(PurchaseOrder order) {
        return new PurchaseOrderDto()
            .setOrderId(order.getOrderId())
            .setOrderDate(order.getOrderDate())
            .setAmount(order.getAmount())
            .setStatus(order.getStatus())
            .setSupplierId(order.getSupplierId())
            .setOrderNumber(order.getOrderNumber())
            .setItems(order.getItems().toList(this::toDto))
            ;
    }

    private PurchaseOrderDto.Item toDto(PurchaseOrderItem purchaseOrderItem) {
        return modelMapper.map(purchaseOrderItem, PurchaseOrderDto.Item.class);
    }

    private PurchaseOrderDto.ItemDetail toDetail(PurchaseOrderItem orderItem) {
        var dto = modelMapper.map(orderItem, PurchaseOrderDto.ItemDetail.class);

        Optional.ofNullable(orderItem.getMaterial())
            .map(PurchaseOrderMaterial::getMaterialId)
            .map(aLong -> modelMapper.map(aLong, MaterialDto.class))
            .ifPresent(dto::setMaterial);

        return dto;
    }

}
