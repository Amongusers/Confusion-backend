package com.example.amongserver.domain.entity;

import lombok.*;

import javax.persistence.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@SequenceGenerator(name = "id_seq_gen", sequenceName = "tir_id_sequence", allocationSize = 1)
@Table(name = "task_in_room",
        indexes = {
                @Index(name = "idx_tir_task_id_fk", columnList = "tir_task_id"),
                @Index(name = "idx_tir_room_id_fk", columnList = "tir_room_id")
        })
public class TaskInRoom extends BaseEntityTemp {

    @Column(name = "tir_id")
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tir_task_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "tir_task_id_fk"))
    private Task task;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tir_room_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "tir_room_id_fk"))
    private Room room;

    @Column(name = "tir_latitude")
    private double latitude;

    @Column(name = "tir_longitude")
    private double longitude;

    @Column(name = "tir_is_active")
    private boolean isActive;

    @Column(name = "tir_is_completed")
    private boolean isCompleted;
}
