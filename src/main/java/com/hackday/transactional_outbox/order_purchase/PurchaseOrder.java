package com.hackday.transactional_outbox.order_purchase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;

@Table(name = "purchase_order")
@Entity
public class PurchaseOrder {

    @Id
    @GeneratedValue
    private final Long id;

    @OneToMany(cascade = {PERSIST, MERGE, REFRESH, DETACH}, orphanRemoval = true )
    @JoinColumn(name = "purchase_order_id", nullable = false, foreignKey = @ForeignKey(name="purchase_order_fk"))
    private final Set<PurchaseOrderItem> orderItems;

    @OneToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH}, orphanRemoval = true, optional = false, fetch = FetchType.LAZY)
    private final Buyer buyer;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private final OffsetDateTime createdAt;

    public static PurchaseOrder of(Long id) {
        return new PurchaseOrder(id, null, Set.of());
    }

    public PurchaseOrder(Buyer buyer, Set<PurchaseOrderItem> orderItems) {
        this(null, buyer, orderItems);
    }

    private PurchaseOrder(Long id, Buyer buyer, Set<PurchaseOrderItem> orderItems) {
        this.id = id;
        this.orderItems = orderItems;
        this.buyer = buyer;
        this.createdAt = OffsetDateTime.now();
    }

    public BigDecimal calculateTotal() {
        return orderItems.stream()
                .map(PurchaseOrderItem::calculateTotal)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public Long getId() {
        return id;
    }

    public Set<PurchaseOrderItem> getOrderItems() {
        return Set.copyOf(orderItems);
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
