package com.motiveschina.erp.endpoint.request;

import com.motiveschina.core.layer.HttpRequest;
import lombok.Data;

@Data
public class InventoryQuery implements HttpRequest {
    private Long productId;
}
