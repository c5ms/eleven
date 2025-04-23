package com.motiveschina.erp.purchase;

import com.motiveschina.erp.purchase.dto.PurchaseOrderDto;
import com.motiveschina.erp.purchase.dto.PurchaseOrderItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

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
}
