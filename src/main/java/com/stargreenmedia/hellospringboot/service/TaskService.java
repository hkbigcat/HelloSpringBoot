package com.stargreenmedia.hellospringboot.service;

import com.stargreenmedia.hellospringboot.entity.Task;
import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<Task> getAllTasks();

    Task createTask(Task task);

    Task createTask2(String title, String description);

    Optional<Task> getTaskById(Long id);

    Task updateTask(Long id, Task updatedTask);

    void deleteTask(Long id);

    Task toggleComplete(Long id);
}