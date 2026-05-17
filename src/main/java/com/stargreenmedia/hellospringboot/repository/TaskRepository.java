package com.stargreenmedia.hellospringboot.repository;

import com.stargreenmedia.hellospringboot.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}