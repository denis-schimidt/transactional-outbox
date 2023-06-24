package com.hackday.transactional_outbox.order_purchase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Table(name = "buyer")
@Entity
public class Buyer {

    @Id
    @GeneratedValue
    private final Long id;

    @Column(length = 50)
    @NotBlank
    private final String name;

    @Column(nullable = false)
    private final LocalDate birthday;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private final OffsetDateTime createdAt;

    public Buyer(long id, String name, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.createdAt = OffsetDateTime.now();
    }

    public static Buyer of(long id) {
        return new Buyer(id, null, null);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }
}
