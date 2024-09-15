package com.example.amongserver.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task_in_room")
public class TaskInRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tir_id")
    private Long id;

    @Column(name = "tir_task_id")
    private Task task;

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
