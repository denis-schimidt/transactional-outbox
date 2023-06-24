package com.hackday.transactional_outbox.order_purchase.controller.input;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PurchaseOrderItemRequest(@NotNull @Min(1) Long productId,
                                       @NotNull @Min(1) @Max(500) Integer quantity,
                                       @NotNull @DecimalMin("0.0") @DecimalMax("0.30") BigDecimal discount) {
}
