package com.hackday.transactional_outbox.order_purchase.controller.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

import static java.util.stream.Collectors.*;

public record PurchaseOrderRequest(@NotNull @Min(1) Long buyerId,
                                   @NotNull @Size(min = 1, max = 300) Set<PurchaseOrderItemRequest> orderItemRequests){

    public Set<Long> getAllProductIds() {
        return orderItemRequests.stream()
                .map(PurchaseOrderItemRequest::productId)
                .collect(toUnmodifiableSet());
    }
};