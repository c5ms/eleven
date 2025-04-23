package com.motiveschina.erp.purchase;

import java.util.UUID;
import java.util.stream.Collectors;
import com.motiveschina.erp.purchase.command.PurchaseOrderCompleteCommand;
import com.motiveschina.erp.purchase.command.PurchaseOrderCreateCommand;
import com.motiveschina.erp.purchase.command.PurchaseOrderDeleteCommand;
import com.motiveschina.erp.purchase.command.PurchaseOrderReviewCommand;
import com.motiveschina.erp.purchase.command.PurchaseOrderSubmitCommand;
import com.motiveschina.erp.support.DomainSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class PurchaseService {

	private final PurchaseOrderManager purchaseOrderManager;
	private final PurchaseOrderRepository purchaseOrderRepository;
	private final PurchaseConvertor purchaseConvertor;

	public PurchaseOrder createPurchaseOrder(PurchaseOrderCreateCommand command) {
		var number = UUID.randomUUID().toString().toUpperCase();
		var order = PurchaseOrder.of(number, command.getSupplierId());

		var items = command.getItems()
				.stream()
				.map(purchaseConvertor::toDomain)
				.collect(Collectors.toSet());

		purchaseOrderManager.createOrder(order,items);
		return order;
	}


	public void submitPurchaseOrder(PurchaseOrderSubmitCommand command) {
		var order = purchaseOrderRepository.findById(command.getOrderId()).orElseThrow(DomainSupport::noPrincipalException);
		purchaseOrderManager.submit(order);
	}

	public void reviewPurchaseOrder(PurchaseOrderReviewCommand command) {
		var order = purchaseOrderRepository.findById(command.getOrderId()).orElseThrow(DomainSupport::noPrincipalException);
		if (command.getPass()) {
			purchaseOrderManager.approve(order);
		} else {
			purchaseOrderManager.reject(order);
		}
	}

	public void deletePurchaseOrder(PurchaseOrderDeleteCommand command) {
		var order = purchaseOrderRepository.findById(command.getOrderId()).orElseThrow(DomainSupport::noPrincipalException);
		purchaseOrderManager.deleteOrder(order);
	}

	public void completePurchaseOrder(PurchaseOrderCompleteCommand command) {
		var order = purchaseOrderRepository.findById(command.getOrderId()).orElseThrow(DomainSupport::noPrincipalException);
		purchaseOrderManager.completeOrder(order);
	}
}
