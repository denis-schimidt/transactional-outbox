package com.hackday.transactional_outbox.order_purchase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "purchase_order_item")
public class PurchaseOrderItem {

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @Id
    private final Product product;

    @Min(1)
    @Max(1000)
    @Column(nullable = false)
    private final Integer quantity;

    @DecimalMin("0.0")
    @DecimalMax("0.30")
    @Column(nullable = false, precision = 15, scale = 2)
    private final BigDecimal discount;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt;

    public PurchaseOrderItem(Product product, Integer quantity, BigDecimal discount) {
        this.product = product;
        this.quantity = quantity;
        this.discount = discount;
        this.createdAt = OffsetDateTime.now();
    }

    public BigDecimal calculateTotal() {
        return product.getPrice()
                .multiply(new BigDecimal(quantity.toString()))
                .subtract(discount);
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PurchaseOrderItem that = (PurchaseOrderItem) o;

        return new EqualsBuilder().append(getProduct(), that.getProduct()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getProduct()).toHashCode();
    }
}
