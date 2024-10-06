package com.example.amongserver.auother.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "game_statistic")
@SequenceGenerator(name = "id_seq_base_with_id", sequenceName = "gs_id_sequence", allocationSize = 1)
@EntityListeners({AuditingEntityListener.class})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "gs_id")),
        @AttributeOverride(name = "createdDate", column = @Column(name = "gs_create_date")),
        @AttributeOverride(name = "lastModifiedDate", column = @Column(name = "gs_update_date")),
        @AttributeOverride(name = "deletedDate", column = @Column(name = "gs_delete_date")),
        @AttributeOverride(name = "isDeleted", column = @Column(name = "gs_is_deleted"))
})
@AssociationOverrides({
        @AssociationOverride(name = "createdBy",
                joinColumns = @JoinColumn(name = "gs_create_user_id")),
        @AssociationOverride(name = "lastModifiedBy",
                joinColumns = @JoinColumn(name = "gs_update_user_id")),
        @AssociationOverride(name = "deletedBy",
                joinColumns = @JoinColumn(name = "gs_delete_user_id"))
})
public class GameStatistic extends BaseEntityWithAudit {

    @Column (name = "gs_latitude", nullable = false, updatable = false)
    private double latitude;

    @Column (name = "gs_longitude", nullable = false, updatable = false)
    private double longitude;

    @Column (name = "gs_radius", nullable = false, updatable = false)
    private double radius;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gs_winner_role_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "gs_winner_role_id_fk"))
    private Role roleWinner;

    @Column(name = "gs_task_complete")
    private Integer taskComplete;

    @Column(name = "gs_killed_users")
    private Integer killedUsers;

    @Column(name = "gs_kicked_out_users")
    private Integer kickedOutUsers;

    @OneToMany(mappedBy = "gameStatistic", fetch = FetchType.LAZY)
    private Set<UserStatistic> userStatisticSet;
}
