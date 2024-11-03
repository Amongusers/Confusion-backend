package com.example.amongserver.domain;

import com.example.amongserver.domain.base.BaseEntityWithAudit;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "role")
@SequenceGenerator(name = "id_seq_base_with_id", sequenceName = "role_id_sequence", allocationSize = 1)
@EntityListeners({AuditingEntityListener.class})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "role_id")),
        @AttributeOverride(name = "createdDate", column = @Column(name = "role_create_date")),
        @AttributeOverride(name = "lastModifiedDate", column = @Column(name = "role_update_date")),
        @AttributeOverride(name = "deletedDate", column = @Column(name = "role_delete_date")),
        @AttributeOverride(name = "isDeleted", column = @Column(name = "role_is_deleted"))
})
@AssociationOverrides({
        @AssociationOverride(name = "createdBy",
                joinColumns = @JoinColumn(name = "role_create_user_id")),
        @AssociationOverride(name = "lastModifiedBy",
                joinColumns = @JoinColumn(name = "role_update_user_id")),
        @AssociationOverride(name = "deletedBy",
                joinColumns = @JoinColumn(name = "role_delete_user_id"))
})
public class Role extends BaseEntityWithAudit {

    @Column(name = "role_name", nullable = false, length = 50)
    private String name;

    @Column(name = "role_description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_parent_id")
    private Role parentRole;

    @OneToMany(mappedBy = "parentRole", fetch = FetchType.LAZY)
    private Set<Role> childrenRoles;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<UserInGame> userInGameSet;

    @OneToMany(mappedBy = "roleWinner", fetch = FetchType.LAZY)
    private Set<GameStatistic> gameStatisticSet;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<UserStatistic> userStatisticSet;
}
