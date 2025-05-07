package com.motiveschina.erp.domain.purchase;

/**
 * This is a demo to demonstrate interceptor pattern.
 * With this pattern, you can design your interceptor for the domain logic to get a high level of extensibility.
 * Ps: use this pattern only if you are sure you need it.
 */
public interface PurchaseOrderInterceptor {

    default void preCreate(PurchaseOrder order) {
    }

    default void afterCreate(PurchaseOrder order) {
    }

    default void preDelete(PurchaseOrder order) {
    }

    default void afterDelete(PurchaseOrder order) {
    }

    default void preSubmit(PurchaseOrder order) {
    }

    default void afterSubmit(PurchaseOrder order) {
    }

    default void preApprove(PurchaseOrder order) {
    }

    default void afterApprove(PurchaseOrder order) {
    }

    default void preReject(PurchaseOrder order) {
    }

    default void afterReject(PurchaseOrder order) {
    }

    default void preComplete(PurchaseOrder order) {
    }

    default void afterComplete(PurchaseOrder order) {
    }


}
