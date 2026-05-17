package com.stargreenmedia.hellospringboot;



import com.stargreenmedia.hellospringboot.TaskController;
import com.stargreenmedia.hellospringboot.entity.Task;
import com.stargreenmedia.hellospringboot.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //@Autowired
    //private WebApplicationContext webApplicationContext;

    @MockitoBean   // ← This is the new annotation
    private TaskService taskService;

    @Test
    @WithMockUser(username = "user", roles = "USER")   // ← Mock logged-in user
    void shouldReturnHomePageWithTasks() throws Exception {
        // Given
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Test Task 1");
        task1.setDescription("Description 1");

        when(taskService.getAllTasks()).thenReturn(List.of(task1));

        // When & Then
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())                    // HTTP 200
                .andExpect(view().name("home"))                // Renders home.html
                .andExpect(model().attributeExists("tasks"))   // Model has "tasks"
                .andExpect(model().attributeExists("username"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldAddNewTask() throws Exception {
        mockMvc.perform(get("/"))  // We can improve this later with POST
                .andExpect(status().isOk());
    }

}