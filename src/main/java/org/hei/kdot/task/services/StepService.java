package org.hei.kdot.task.services;

import org.hei.kdot.task.models.Step;
import org.hei.kdot.task.repositories.StepRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class StepService {

    private final StepRepository stepRepository;

    public StepService(StepRepository stepRepository) {
        this.stepRepository = stepRepository;
    }

    public Step findById(String id) throws SQLException {
        return stepRepository.findById(id);
    }

    public List<Step> findAll() throws SQLException {
        return stepRepository.findAll();
    }

    public List<Step> findByTaskId(String taskId) throws SQLException {
        return stepRepository.findByTaskId(taskId);
    }

    public Step create(Step step) throws SQLException {
        return stepRepository.save(step);
    }

    public boolean deleteById(String id) throws SQLException {
        return stepRepository.deleteById(id);
    }

    public List<Step> findByTaskIdAndIsCompleted(
            String taskId,
            boolean isCompleted
    ) throws SQLException {

        return stepRepository.findByTaskIdAndIsCompleted(
                taskId,
                isCompleted
        );
    }
}