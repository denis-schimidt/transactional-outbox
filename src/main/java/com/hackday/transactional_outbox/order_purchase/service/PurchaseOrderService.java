package com.hackday.transactional_outbox.order_purchase.service;

import com.hackday.transactional_outbox.order_purchase.PurchaseOrderRepository;
import com.hackday.transactional_outbox.order_purchase.PurchaseOrder;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PurchaseOrderService {
    private final PurchaseOrderRepository repository;

    @Transactional
    public PurchaseOrder createOrder(PurchaseOrder purchaseOrder) {
        return repository.save(purchaseOrder);
    }
}
