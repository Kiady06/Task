package org.hei.kdot.task.repositories;

import org.hei.kdot.task.config.DatabaseConnection;
import org.hei.kdot.task.models.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private final DatabaseConnection databaseConnection;

    public UserRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public User findById(String id) throws SQLException {
        String sql = """
                SELECT id, username
                FROM "user"
                WHERE id = ?
                """;

        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getString("id"),
                            resultSet.getString("username")
                    );
                }
            }
        }

        return null;
    }

    public List<User> findAll() throws SQLException {
        String sql = """
                SELECT id, username
                FROM "user"
                """;

        List<User> users = new ArrayList<>();

        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getString("id"),
                        resultSet.getString("username")
                ));
            }
        }

        return users;
    }

    public User save(User user) throws SQLException {
        String sql = """
                INSERT INTO "user" (id, username)
                VALUES (?, ?)
                """;

        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, user.getId());
            statement.setString(2, user.getUserName());

            statement.executeUpdate();
        }

        return user;
    }

    public boolean deleteById(String id) throws SQLException {
        String sql = """
                DELETE FROM "user"
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