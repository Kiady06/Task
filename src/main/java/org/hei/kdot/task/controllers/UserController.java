/*
    should not put these ifs inside my controllers </3
 */

package org.hei.kdot.task.controllers;

import org.hei.kdot.task.models.Step;
import org.hei.kdot.task.models.Task;
import org.hei.kdot.task.models.User;
import org.hei.kdot.task.services.StepService;
import org.hei.kdot.task.services.TaskService;
import org.hei.kdot.task.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final TaskService taskService;
    private final UserService userService;
    private final StepService stepService;

    public UserController(TaskService taskService, UserService userService, StepService stepService) {
        this.taskService = taskService;
        this.userService = userService;
        this.stepService = stepService;
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user) throws SQLException {
        User createdUser = userService.create(user);

        return ResponseEntity
                .status(201)
                .body(createdUser);
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<?> getUserTasks(
            @PathVariable String id
    ) throws SQLException {

        User user = userService.findById(id);

        if (user == null) {
            return ResponseEntity
                    .status(404)
                    .body("User with id '" + id + "' not found.");
        }

        List<Task> tasks = taskService.findByUserId(id);

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{userId}/tasks/{taskId}/steps")
    public ResponseEntity<?> getTaskSteps(
            @PathVariable String userId,
            @PathVariable String taskId,
            @RequestParam(defaultValue = "true") boolean isCompleted
    ) throws SQLException {

        User user = userService.findById(userId);

        if (user == null) {
            return ResponseEntity
                    .status(404)
                    .body("User with id '" + userId + "' not found.");
        }

        Task task = taskService.findById(taskId);

        if (task == null) {
            return ResponseEntity
                    .status(404)
                    .body("Task with id '" + taskId + "' not found.");
        }

        if (!task.getIdUser().equals(userId)) {
            return ResponseEntity
                    .status(404)
                    .body("Task with id '" + taskId +
                            "' does not belong to user with id '" + userId + "'.");
        }

        List<Step> steps = stepService.findByTaskIdAndIsCompleted(
                taskId,
                isCompleted
        );

        return ResponseEntity.ok(steps);
    }
}
