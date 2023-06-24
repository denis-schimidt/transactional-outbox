package com.hackday.transactional_outbox.order_purchase.service;

import com.hackday.transactional_outbox.order_purchase.PurchaseOrderItem;
import com.hackday.transactional_outbox.order_purchase.controller.input.PurchaseOrderRequest;
import com.hackday.transactional_outbox.order_purchase.controller.output.BuyerResponse;
import com.hackday.transactional_outbox.order_purchase.controller.output.ProductResponse;
import com.hackday.transactional_outbox.order_purchase.BuyerRepository;
import com.hackday.transactional_outbox.order_purchase.Product;
import com.hackday.transactional_outbox.order_purchase.ProductRepository;
import com.hackday.transactional_outbox.order_purchase.PurchaseOrder;
import com.hackday.transactional_outbox.order_purchase.controller.output.PurchaseOrderItemResponse;
import com.hackday.transactional_outbox.order_purchase.controller.output.PurchaseOrderResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Component
@AllArgsConstructor
public class PurchaseOrderConverter {
    private final ProductRepository productRepository;
    private final BuyerRepository buyerRepository;

    public PurchaseOrder convertToEntity(PurchaseOrderRequest request) {
        var buyer = buyerRepository.findById(request.buyerId())
                .orElseThrow(() -> new IllegalArgumentException("Buyer %d DOES NOT exist".formatted(request.buyerId())));

        var productById = productRepository.findAllById(request.getAllProductIds())
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        var itens = request.orderItemRequests()
                .stream()
                .map(i -> {
                    var product = productById.get(i.productId());

                    if (product == null) {
                        throw new IllegalArgumentException("Product %d DOES NOT exist".formatted(i.productId()));
                    }

                    return new PurchaseOrderItem(product, i.quantity(), i.discount());
                })
                .collect(toUnmodifiableSet());

        return new PurchaseOrder(buyer, itens);
    }

    public PurchaseOrderResponse toResponse(PurchaseOrder purchaseOrder) {

        var orderItemResponses = purchaseOrder.getOrderItems()
                .stream()
                .map(i -> new PurchaseOrderItemResponse(
                        new ProductResponse(i.getProduct().getId(), i.getProduct().getName(), i.getProduct().getDescription(), i.getProduct().getPrice()),
                        i.getQuantity(), i.getDiscount()))
                .collect(toUnmodifiableSet());

        var buyer =  purchaseOrder.getBuyer();

        return new PurchaseOrderResponse(purchaseOrder.getId(), new BuyerResponse(buyer.getId(), buyer.getName(), buyer.getBirthday()), orderItemResponses);
    }
}
