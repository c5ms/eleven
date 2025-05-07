package com.motiveschina.erp.endpoint.model;

import com.motiveschina.core.layer.HttpRequest;
import lombok.Data;

@Data
public class InventoryQuery implements HttpRequest {
    private Long materialId;
}
