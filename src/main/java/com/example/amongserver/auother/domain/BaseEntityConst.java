package com.example.amongserver.auother.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@MappedSuperclass
@SQLDelete(sql = "UPDATE #{entityName} SET is_deleted = true, delete_date = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "is_deleted = false")
public abstract class BaseEntityConst {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq_base_const")
    @Column(name = "id")
    private Long id;

    @CreatedDate
    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "delete_date")
    private LocalDateTime deleteDate;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;
}
