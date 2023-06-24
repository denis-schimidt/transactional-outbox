package com.hackday.transactional_outbox.order_purchase.controller;

import com.hackday.transactional_outbox.order_purchase.controller.input.PurchaseOrderRequest;
import com.hackday.transactional_outbox.order_purchase.service.PurchaseOrderConverter;
import com.hackday.transactional_outbox.order_purchase.service.PurchaseOrderService;
import com.hackday.transactional_outbox.order_purchase.controller.output.PurchaseOrderResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final PurchaseOrderService service;
    private final PurchaseOrderConverter converter;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseOrderResponse createOrder(@Valid PurchaseOrderRequest orderRequest) {
        var purchaseOrder = service.createOrder(converter.convertToEntity(orderRequest));

        return converter.toResponse(purchaseOrder);
    }
}
