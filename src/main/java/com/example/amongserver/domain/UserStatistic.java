package com.example.amongserver.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "user_statistic", indexes = {
        @Index(name = "idx_us_user_id_fk", columnList = "us_user_id"),
        @Index(name = "idx_us_gs_id_fk", columnList = "us_gs_id"),
        @Index(name = "idx_us_role_id_fk", columnList = "us_role_id")
},uniqueConstraints = @UniqueConstraint(name = "idx_unique_user_game",
        columnNames = {"us_gs_id", "us_user_id"}))
@SequenceGenerator(name = "id_seq_base_with_id", sequenceName = "us_id_sequence", allocationSize = 1)
@EntityListeners({AuditingEntityListener.class})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "us_id")),
        @AttributeOverride(name = "createdDate", column = @Column(name = "us_create_date")),
        @AttributeOverride(name = "lastModifiedDate", column = @Column(name = "us_update_date")),
        @AttributeOverride(name = "deletedDate", column = @Column(name = "us_delete_date")),
        @AttributeOverride(name = "isDeleted", column = @Column(name = "us_is_deleted"))
})
@AssociationOverrides({
        @AssociationOverride(name = "createdBy",
                joinColumns = @JoinColumn(name = "us_create_user_id")),
        @AssociationOverride(name = "lastModifiedBy",
                joinColumns = @JoinColumn(name = "us_update_user_id")),
        @AssociationOverride(name = "deletedBy",
                joinColumns = @JoinColumn(name = "us_delete_user_id"))
})
public class UserStatistic extends BaseEntityWithAudit {

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "us_user_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "us_user_id_fk"))
    private User user;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "us_gs_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "us_gs_id_fk"))
    private GameStatistic gameStatistic;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "us_role_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "us_role_id_fk"))
    private Role role;

    @Column(name = "us_victory_status")
    private byte victoryStatus;

    @Column(name = "us_task_complete")
    private Integer taskComplete;

    @Column(name = "us_killed_users")
    private Integer killedUsers;
}
