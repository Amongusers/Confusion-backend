package com.example.amongserver.auother.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "task")
@SequenceGenerator(name = "id_seq_base_with_id", sequenceName = "task_id_sequence", allocationSize = 1)
@EntityListeners({AuditingEntityListener.class})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "task_id")),
        @AttributeOverride(name = "createdDate", column = @Column(name = "task_create_date")),
        @AttributeOverride(name = "lastModifiedDate", column = @Column(name = "task_update_date")),
        @AttributeOverride(name = "deletedDate", column = @Column(name = "task_delete_date")),
        @AttributeOverride(name = "isDeleted", column = @Column(name = "task_is_deleted"))
})
@AssociationOverrides({
        @AssociationOverride(name = "createdBy",
                joinColumns = @JoinColumn(name = "task_create_user_id")),
        @AssociationOverride(name = "lastModifiedBy",
                joinColumns = @JoinColumn(name = "task_update_user_id")),
        @AssociationOverride(name = "deletedBy",
                joinColumns = @JoinColumn(name = "task_delete_user_id"))
})
public class Task extends BaseEntityWithAudit {

    @Column(name = "task_title", nullable = false, unique = true, length = 30)
    private String title;

    @Column(name = "task_description", length = 2000)
    private String description;

}