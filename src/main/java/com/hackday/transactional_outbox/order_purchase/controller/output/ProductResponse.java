package com.hackday.transactional_outbox.order_purchase.controller.output;

import java.math.BigDecimal;

public record ProductResponse(Long id, String name, String description, BigDecimal price){};
