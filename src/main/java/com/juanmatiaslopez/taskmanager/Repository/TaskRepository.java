package com.juanmatiaslopez.taskmanager.Repository;

import com.juanmatiaslopez.taskmanager.Enum.Category;
import com.juanmatiaslopez.taskmanager.Model.Task;
import com.juanmatiaslopez.taskmanager.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);
    List<Task> findByUserAndCategory(User user, Category category);
}
