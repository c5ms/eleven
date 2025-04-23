package com.motiveschina.erp.purchase;

import com.eleven.framework.domain.DomainError;
import com.eleven.framework.domain.SimpleDomainError;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PurchaseErrors {
    DomainError ORDER_NOT_SUBMITTED = SimpleDomainError.of("order_not_submitted", "the Purchase order has not been submitted");
    DomainError ORDER_NOT_APPROVED = SimpleDomainError.of("order_not_approved", "the Purchase order has not been approved");
}
