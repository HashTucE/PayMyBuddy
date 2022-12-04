package com.openclassrooms.paymybuddy.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class LoginIT {


    @Autowired
    private MockMvc mockMvc;


    @Test
    void viewLoginIT() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(unauthenticated())
                .andReturn();
    }


    @Test
    void viewLoginIT2() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(unauthenticated())
                .andReturn();
    }


    @Test
    void viewRegisterIT() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(unauthenticated())
                .andExpect(model().attributeExists("userDto"))
                .andExpect(view().name("register"))
                .andReturn();
    }


    @Test
    @WithMockUser(username = "test@paymybuddy.com")
    void createNewUserIT() throws Exception {

        mockMvc.perform(post("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andReturn();
    }
}
