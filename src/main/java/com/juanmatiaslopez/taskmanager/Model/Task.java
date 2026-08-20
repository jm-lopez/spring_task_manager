package com.juanmatiaslopez.taskmanager.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.juanmatiaslopez.taskmanager.Enum.Category;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tasks")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    private boolean isCompleted = false;

    @ManyToOne()
    @JoinColumn(name = "users_id")
    @JsonBackReference(value = "user_tasks")
    private User user;
}
