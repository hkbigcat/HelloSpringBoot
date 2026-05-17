package com.stargreenmedia.hellospringboot.service;

import com.stargreenmedia.hellospringboot.entity.Task;
import com.stargreenmedia.hellospringboot.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task sampleTask;

    @BeforeEach
    void setUp() {
        sampleTask = new Task();
        sampleTask.setId(1L);
        sampleTask.setTitle("Test Task");
        sampleTask.setDescription("Test Description");
    }

    @Test
    void shouldCreateNewTask() {
        // Given
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

        // When
        Task taskToCreate = new Task();
        taskToCreate.setTitle("Test Task");
        taskToCreate.setDescription("Test Description");

        Task created = taskService.createTask(taskToCreate);

        // Then
        assertThat(created).isNotNull();
        assertThat(created.getTitle()).isEqualTo("Test Task");
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void shouldReturnAllTasks() {
        // Given
        when(taskRepository.findAll()).thenReturn(List.of(sampleTask));

        // When
        List<Task> tasks = taskService.getAllTasks();

        // Then
        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getTitle()).isEqualTo("Test Task");
    }

    @Test
    void shouldToggleCompleteStatus() {
        // Given
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

        // When
        Task toggled = taskService.toggleComplete(1L);

        // Then
        assertThat(toggled.isCompleted()).isTrue();
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFoundForToggle() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> taskService.toggleComplete(999L));
    }
}