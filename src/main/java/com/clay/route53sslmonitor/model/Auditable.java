package com.clay.route53sslmonitor.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@Data
public class Auditable implements Serializable {

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    protected Instant createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    protected Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}