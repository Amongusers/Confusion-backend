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

    // TODO: переопределить equals и hashCode
//    @Override
//    public boolean equals(final Object o) {
//        if (o == this) return true;
//        if (!(o instanceof Task)) return false;
//        final Task other = (Task) o;
//        if (!other.canEqual((Object) this)) return false;
//        final Object this$id = this.id;
//        final Object other$id = other.id;
//        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
//        final Object this$title = this.title;
//        final Object other$title = other.title;
//        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
//        final Object this$description = this.description;
//        final Object other$description = other.description;
//        if (this$description == null ? other$description != null : !this$description.equals(other$description))
//            return false;
//        return true;
//    }
//
//    protected boolean canEqual(final Object other) {
//        return other instanceof Task;
//    }
//
//    @Override
//    public int hashCode() {
//        final int PRIME = 59;
//        int result = 1;
//        final Object $id = this.id;
//        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
//        final Object $title = this.title;
//        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
//        final Object $description = this.description;
//        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
//        return result;
//    }
}