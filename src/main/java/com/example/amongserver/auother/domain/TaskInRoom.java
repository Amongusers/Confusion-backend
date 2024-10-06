package com.example.amongserver.auother.domain;

import lombok.*;

import javax.persistence.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "task_in_room",
        indexes = {
                @Index(name = "idx_tir_task_id_fk", columnList = "tir_task_id"),
                @Index(name = "idx_tir_room_id_fk", columnList = "tir_room_id")
        }, uniqueConstraints = @UniqueConstraint(name = "idx_unique_task",
        columnNames = {"tir_task_id", "tir_room_id", "tir_latitude", "tir_longitude"}))
@SequenceGenerator(name = "id_seq_base_with_id", sequenceName = "tir_id_sequence", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "tir_id"))
public class TaskInRoom extends BaseEntityWithId {

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tir_task_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "tir_task_id_fk"))
    private Task task;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tir_room_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "tir_room_id_fk"))
    private Room room;

    @Column(name = "tir_latitude", nullable = false, updatable = false)
    private double latitude;

    @Column(name = "tir_longitude", nullable = false, updatable = false)
    private double longitude;

    @Column(name = "tir_is_completed", nullable = false,
            columnDefinition = "boolean default false")
    private boolean isCompleted;
}