package org.hei.kdot.task.services;

import org.hei.kdot.task.models.User;
import org.hei.kdot.task.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(String id) throws SQLException {
        return userRepository.findById(id);
    }

    public List<User> findAll() throws SQLException {
        return userRepository.findAll();
    }

    public User create(User user) throws SQLException {
        return userRepository.save(user);
    }

    public boolean deleteById(String id) throws SQLException {
        return userRepository.deleteById(id);
    }
}