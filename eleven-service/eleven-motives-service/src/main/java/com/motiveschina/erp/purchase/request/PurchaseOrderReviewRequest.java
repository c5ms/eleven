package com.motiveschina.erp.purchase.request;

import com.motiveschina.erp.support.layer.HttpRequest;
import lombok.Data;

@Data
public class PurchaseOrderReviewRequest implements HttpRequest {
    private Boolean pass;
}
