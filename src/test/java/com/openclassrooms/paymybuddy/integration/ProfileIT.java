package com.openclassrooms.paymybuddy.integration;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileIT {


    @Autowired
    private MockMvc mockMvc;



    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ProfileController()).build();
    }



    @Test
    void viewProfileIT() throws Exception {
        this.mockMvc.perform(get("/profile").with(user("test@paymybuddy.com")))
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(model().attributeExists("bankName"))
                .andExpect(model().attributeExists("accountNumber"))
                .andExpect(model().attributeExists("contacts"))
                .andExpect(view().name("profile"))
                .andReturn();
    }


//    @Test
//    void addAccountIT() throws Exception {
//
//        this.mockMvc.perform(post("/profile/addAccount").with(user("test@paymybuddy.com"))
//                        .param("accountDto"))
//                .andExpect(status().isFound())
//                .andExpect(view().name("redirect:/profile"))
//                .andExpect(redirectedUrl("/profile"))
//                .andReturn();
//    }
//
//
//
//    @Test
//    void deleteAccountIT() throws Exception {
//
//        this.mockMvc.perform(post("/profile/deleteAccount").with(user("test@paymybuddy.com")))
//                .andExpect(status().isFound())
//                .andExpect(view().name("redirect:/profile"))
//                .andExpect(redirectedUrl("/profile"))
//                .andReturn();
//    }
//
//
//    @Test
//    void addContactIT() throws Exception {
//
//        this.mockMvc.perform(post("/profile/addContact").with(user("test@paymybuddy.com"))
//                        .param("contactDto"))
//                .andExpect(status().isFound())
//                .andExpect(view().name("redirect:/profile"))
//                .andExpect(redirectedUrl("/profile"))
//                .andReturn();
//    }
//
//
//
//    @Test
//    void deleteContactIT() throws Exception {
//
//        this.mockMvc.perform(post("/profile/deleteContact").with(user("test@paymybuddy.com")))
//                .andExpect(status().isFound())
//                .andExpect(view().name("redirect:/profile"))
//                .andExpect(redirectedUrl("/profile"))
//                .andReturn();
//    }
}
