package com.motiveschina.erp.domain.purchase;

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
