package org.hei.kdot.task.repositories;

import org.hei.kdot.task.config.DatabaseConnection;
import org.hei.kdot.task.models.Task;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository {

    private final DatabaseConnection databaseConnection;

    public TaskRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public Task findById(String id) throws SQLException {
        String sql = """
                SELECT id, name, id_user
                FROM task
                WHERE id = ?
                """;

        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Task(
                            resultSet.getString("id"),
                            resultSet.getString("name"),
                            resultSet.getString("id_user")
                    );
                }
            }
        }

        return null;
    }

    public List<Task> findAll() throws SQLException {
        String sql = """
                SELECT id, name, id_user
                FROM task
                """;

        List<Task> tasks = new ArrayList<>();

        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                tasks.add(new Task(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("id_user")
                ));
            }
        }

        return tasks;
    }

    public List<Task> findByUserId(String userId) throws SQLException {
        String sql = """
                SELECT id, name, id_user
                FROM task
                WHERE id_user = ?
                """;

        List<Task> tasks = new ArrayList<>();

        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    tasks.add(new Task(
                            resultSet.getString("id"),
                            resultSet.getString("name"),
                            resultSet.getString("id_user")
                    ));
                }
            }
        }

        return tasks;
    }

    public Task save(Task task) throws SQLException {
        String sql = """
                INSERT INTO task (id, name, id_user)
                VALUES (?, ?, ?)
                """;

        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, task.getId());
            statement.setString(2, task.getName());
            statement.setString(3, task.getIdUser());

            statement.executeUpdate();
        }

        return task;
    }

    public boolean deleteById(String id) throws SQLException {
        String sql = """
                DELETE FROM task
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
}