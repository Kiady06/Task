package org.hei.kdot.task.repositories;

import org.hei.kdot.task.config.DatabaseConnection;
import org.hei.kdot.task.models.Step;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StepRepository {

    private final DatabaseConnection databaseConnection;

    public StepRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public Step findById(String id) throws SQLException {
        String sql = """
                SELECT id, title, description, is_completed, id_task
                FROM step
                WHERE id = ?
                """;

        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Step(
                            resultSet.getString("id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            resultSet.getBoolean("is_completed"),
                            resultSet.getString("id_task")
                    );
                }
            }
        }

        return null;
    }

    public List<Step> findAll() throws SQLException {
        String sql = """
                SELECT id, title, description, is_completed, id_task
                FROM step
                """;

        List<Step> steps = new ArrayList<>();

        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                steps.add(new Step(
                        resultSet.getString("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("is_completed"),
                        resultSet.getString("id_task")
                ));
            }
        }

        return steps;
    }

    public List<Step> findByTaskId(String taskId) throws SQLException {
        String sql = """
                SELECT id, title, description, is_completed, id_task
                FROM step
                WHERE id_task = ?
                """;

        List<Step> steps = new ArrayList<>();

        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, taskId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    steps.add(new Step(
                            resultSet.getString("id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            resultSet.getBoolean("is_completed"),
                            resultSet.getString("id_task")
                    ));
                }
            }
        }

        return steps;
    }

    public Step save(Step step) throws SQLException {
        String sql = """
                INSERT INTO step (id, title, description, is_completed, id_task)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, step.getId());
            statement.setString(2, step.getTitle());
            statement.setString(3, step.getDescription());
            statement.setBoolean(4, step.isCompleted());
            statement.setString(5, step.getIdTask());

            statement.executeUpdate();
        }

        return step;
    }

    public boolean deleteById(String id) throws SQLException {
        String sql = """
                DELETE FROM step
                WHERE id = ?
                """;

        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, id);

            return statement.executeUpdate() > 0;
        }
    }

    public List<Step> findByTaskIdAndIsCompleted(
            String taskId,
            boolean isCompleted
    ) throws SQLException {

        String sql = """
            SELECT id, title, description, is_completed, id_task
            FROM step
            WHERE id_task = ?
            AND is_completed = ?
            """;

        List<Step> steps = new ArrayList<>();

        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, taskId);
            statement.setBoolean(2, isCompleted);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    steps.add(new Step(
                            resultSet.getString("id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            resultSet.getBoolean("is_completed"),
                            resultSet.getString("id_task")
                    ));
                }
            }
        }

        return steps;
    }
}