package com.stargreenmedia.hellospringboot;

import com.stargreenmedia.hellospringboot.entity.Task;
import com.stargreenmedia.hellospringboot.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;


@Controller
class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public String home(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);

        // Add current username for the template
        String username = org.springframework.security.core.context.SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        model.addAttribute("username", username);

        return "home";
    }

    @PostMapping("/add")
    public String addTask(@RequestParam String title,
                          @RequestParam String description) {

        taskService.createTask2(title, description);
        return "redirect:/";
    }
}