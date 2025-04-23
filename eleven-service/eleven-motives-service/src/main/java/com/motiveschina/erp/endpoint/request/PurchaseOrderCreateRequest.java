package com.motiveschina.erp.endpoint.request;

import java.util.List;
import com.motiveschina.erp.application.dto.PurchaseOrderItemDto;
import com.motiveschina.core.layer.HttpRequest;
import lombok.Data;

@Data
public class PurchaseOrderCreateRequest implements HttpRequest {

    private Long supplierId;

    private List<PurchaseOrderItemDto> items;
}
