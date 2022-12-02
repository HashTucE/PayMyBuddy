package com.openclassrooms.paymybuddy.integration;

import com.openclassrooms.paymybuddy.controller.LoginController;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class LoginIT {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new LoginController()).build();
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .apply(springSecurity())
//                .build();
    }


    @Test
    void viewLoginIT() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(unauthenticated())
                .andReturn();
    }


    @Test
    void viewLoginIT2() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(unauthenticated())
                .andReturn();
    }


    @Test
    void viewRegisterIT() throws Exception {
        this.mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(unauthenticated())
                .andExpect(model().attributeExists("userDto"))
                .andExpect(view().name("register"))
                .andReturn();
    }


    @Test
    @WithAnonymousUser
    void createNewUserIT() throws Exception {
//        this.mockMvc.perform(post("/register"))
//                .andExpect(status().isCreated())
//                .andExpect(model().attribute("successMessage", "User has been registered successfully"))
//                .andExpect(model().attributeExists("user"))
//                .andExpect(view().name("register"))
//                .andReturn();



//        mockMvc.perform(post("/register")
//                        .with(csrf())
//                        .param("action", "signup"))
//                .andExpect(status().isOk());
    }













//    public static String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

}
