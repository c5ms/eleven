package com.motiveschina.erp.endpoint.request;

import com.motiveschina.core.layer.HttpRequest;
import lombok.Data;

@Data
public class PurchaseOrderQuery implements HttpRequest {
    private String status;
}
