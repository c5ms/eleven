package com.motiveschina.erp.endpoint.request;

import com.motiveschina.core.layer.HttpRequest;
import com.motiveschina.erp.application.dto.PurchaseOrderItemDto;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseOrderCreateRequest implements HttpRequest {

    private Long supplierId;

    private List<PurchaseOrderItemDto> items;
}
