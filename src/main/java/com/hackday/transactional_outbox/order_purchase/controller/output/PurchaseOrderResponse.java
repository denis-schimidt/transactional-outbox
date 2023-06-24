package com.hackday.transactional_outbox.order_purchase.controller.output;

import java.util.Set;

public record PurchaseOrderResponse(Long id,
                                    BuyerResponse buyer,
                                    Set<PurchaseOrderItemResponse> orderItemRequests){};