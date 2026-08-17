package org.hei.kdot.task.services;

import org.hei.kdot.task.models.Task;
import org.hei.kdot.task.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task findById(String id) throws SQLException {
        return taskRepository.findById(id);
    }

    public List<Task> findAll() throws SQLException {
        return taskRepository.findAll();
    }

    public List<Task> findByUserId(String userId) throws SQLException {
        return taskRepository.findByUserId(userId);
    }

    public Task create(Task task) throws SQLException {
        return taskRepository.save(task);
    }

    public boolean deleteById(String id) throws SQLException {
        return taskRepository.deleteById(id);
    }
}