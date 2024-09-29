package com.example.amongserver.auother.domain;

import com.example.amongserver.registration.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@MappedSuperclass
@SQLDelete(sql = "UPDATE #{entityName} SET is_deleted = true, delete_date = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "is_deleted = false")
public class BaseEntityWithAudit extends AbstractAuditable<User, Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq_base_with_audit")
    @Column(name = "id")
    @Override
    public Long getId() {
        return super.getId();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "delete_by")
    private User deletedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "delete_date")
    private LocalDateTime deleteDate;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;
}
