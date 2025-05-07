package com.motiveschina.erp.endpoint.model;

import java.util.List;
import com.motiveschina.core.layer.HttpRequest;
import com.motiveschina.erp.application.model.PurchaseOrderDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PurchaseOrderCreateRequest implements HttpRequest {

    @NotNull
    private Long supplierId;

    @NotEmpty
    private List<PurchaseOrderDto.Item> items;
}
