package com.hackday.transactional_outbox.order_purchase.controller.output;

import java.math.BigDecimal;

public record PurchaseOrderItemResponse(ProductResponse product,
                                        Integer quantity,
                                        BigDecimal discount) {}
