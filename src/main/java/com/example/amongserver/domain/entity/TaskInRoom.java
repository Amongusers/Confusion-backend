
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
@Table(name = "task_in_room")
public class TaskInRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tir_id_seq")
    @SequenceGenerator(name = "tir_id_seq", sequenceName = "tir_id_sequence", allocationSize = 1)
    @Column(name = "tir_id")
    private Long id;

    @ToString.Exclude
    @Column(name = "tir_task_id")
    private Task task;

    @ToString.Exclude
    @Column(name = "tir_room_id")
    private Room room;

    @Column (name = "tir_latitude")
    private double latitude;

    @Column (name = "tir_longitude")
    private double longitude;

    @Column (name = "tir_is_active")
    private boolean isActive;

    @Column (name = "tir_is_completed")
    private boolean isCompleted;
}