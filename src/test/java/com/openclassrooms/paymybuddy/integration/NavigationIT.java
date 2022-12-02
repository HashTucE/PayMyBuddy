package com.openclassrooms.paymybuddy.integration;

import com.openclassrooms.paymybuddy.controller.NavigationController;
import com.openclassrooms.paymybuddy.controller.ProfileController;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class NavigationIT {


    @Autowired
    private MockMvc mockMvc;


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new NavigationController()).build();
    }


    @Test
    void viewHomeIT() throws Exception {
        this.mockMvc.perform(get("/home").with(user("test@paymybuddy.com")))
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(model().attributeExists("principal"))
                .andExpect(model().attributeExists("balance"))
                .andExpect(model().attributeExists("bankName"))
                .andExpect(model().attributeExists("accountNumber"))
                .andExpect(model().attributeExists("transactions"))
                .andExpect(view().name("home"))
                .andReturn();
    }


    @Test
    void viewContactIT() throws Exception {
        this.mockMvc.perform(get("/contact").with(user("test@paymybuddy.com")))
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(view().name("contact"))
                .andReturn();
    }


    @Test
    void viewRegisterIT() throws Exception {
        this.mockMvc.perform(get("/forgotPassword").with(user("test@paymybuddy.com")))
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(view().name("forgotPassword"))
                .andReturn();
    }

}
