package com.example.amongserver.auother.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@SequenceGenerator(name = "id_seq_base_with_audit", sequenceName = "role_id_sequence", allocationSize = 1)
@Table(name = "role")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "role_id")),
        @AttributeOverride(name = "createDate", column = @Column(name = "role_create_date")),
        @AttributeOverride(name = "update_date", column = @Column(name = "role_update_date")),
        @AttributeOverride(name = "delete_date", column = @Column(name = "role_delete_date")),
        @AttributeOverride(name = "is_deleted", column = @Column(name = "role_is_deleted"))
})
public class Role extends BaseEntityConst {

    @Column (name = "role_class", nullable = false)
    private String roleClass;

    @Column (name = "role_type", nullable = false)
    private String type;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<UserInGame> userInGameSet;
}
