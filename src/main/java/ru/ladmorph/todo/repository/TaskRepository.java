package ru.ladmorph.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ladmorph.todo.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
