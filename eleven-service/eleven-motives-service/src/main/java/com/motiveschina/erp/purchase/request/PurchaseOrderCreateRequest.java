package com.motiveschina.erp.purchase.request;

import java.util.List;
import com.motiveschina.erp.purchase.dto.PurchaseOrderItemDto;
import com.motiveschina.erp.support.layer.HttpRequest;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

@Data
public class PurchaseOrderCreateRequest implements HttpRequest {

    Long supplierId;

    List<PurchaseOrderItemDto> items;
}
