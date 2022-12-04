package com.openclassrooms.paymybuddy.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class NavigationIT {


    @Autowired
    private MockMvc mockMvc;



    @Test
    @WithMockUser(username = "test@paymybuddy.com")
    void viewHomeIT() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(model().attributeExists("principal"))
                .andExpect(model().attributeExists("balance"))
                .andExpect(model().attributeExists("transactions"))
                .andExpect(view().name("home"))
                .andReturn();
    }


    @Test
    @WithMockUser(username = "test@paymybuddy.com")
    void viewContactIT() throws Exception {
        mockMvc.perform(get("/contact"))
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(view().name("contact"))
                .andReturn();
    }


    @Test
    @WithMockUser(username = "test@paymybuddy.com")
    void viewRegisterIT() throws Exception {
        mockMvc.perform(get("/forgotPassword"))
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(view().name("forgotPassword"))
                .andReturn();
    }

}
