package be.africshop.africshopbackend.utils;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;


@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CommonEntity implements Serializable {

    @Enumerated(EnumType.STRING)
    private DataStatus dataStatus;

    @Column(name = "code_auto", length = 10)
    private String codeAuto;

    @CreatedBy
    @Column(length = 36, name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "date_created", updatable = false)
    @CreatedDate
    @CreationTimestamp
    private Instant dateCreated;

    @LastModifiedBy
    @Column(length = 36)
    private String updatedBy;

    @LastModifiedDate
    @UpdateTimestamp
    private Instant dateUpdated;

    @PrePersist
    public void prePersist() {
        this.dataStatus = DataStatus.CREATED;
    }

    @PreUpdate
    public void preUpdate() {
        //this.dataStatus = DataStatus.UPDATED;
    }

}
