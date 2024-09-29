package com.example.amongserver.auother.domain;

import com.example.amongserver.registration.domain.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@MappedSuperclass
@SQLDelete(sql = "UPDATE #{entityName} SET is_deleted = true, delete_date = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "is_deleted = false")
public class BaseEntityWithAudit extends BaseEntityWithId {

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modified_by")
    private User lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delete_by")
    private User deletedBy;

    @Column(name = "delete_date")
    private LocalDateTime deleteDate;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;
}
