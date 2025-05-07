package com.motiveschina.erp.endpoint.model;

import com.motiveschina.core.layer.HttpRequest;
import lombok.Data;

@Data
public class PurchaseOrderQuery implements HttpRequest {
    private String status;
}
