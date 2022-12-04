package com.openclassrooms.paymybuddy.integration;

import com.openclassrooms.paymybuddy.dto.AccountDto;
import com.openclassrooms.paymybuddy.dto.ContactDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ProfileIT {


    @Autowired
    private MockMvc mockMvc;



    @Test
    @WithMockUser(username = "test@paymybuddy.com")
    void viewProfileIT() throws Exception {
        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(model().attributeExists("bankName"))
                .andExpect(model().attributeExists("accountNumber"))
                .andExpect(model().attributeExists("contacts"))
                .andExpect(model().size(5))
                .andExpect(view().name("profile"))
                .andReturn();
    }


    @Test
    @WithMockUser(username = "test@paymybuddy.com")
    void addAccountIT() throws Exception {

        mockMvc.perform(post("/profile/addAccount")
                        .sessionAttr("accountDTO", new AccountDto())
                        .param("bankName", "name")
                        .param("accountNumber", "number"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/profile"))
                .andExpect(redirectedUrl("/profile"))
                .andReturn();
    }



    @Test
    @WithMockUser(username = "test@paymybuddy.com")
    void deleteAccountIT() throws Exception {

        mockMvc.perform(delete("/profile/deleteAccount"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/profile"))
                .andExpect(redirectedUrl("/profile"))
                .andReturn();
    }


    @Test
    @WithMockUser(username = "test@paymybuddy.com")
    void addContactIT() throws Exception {



        mockMvc.perform(post("/profile/addContact")
                        .sessionAttr("contactDTO", new ContactDto())
                        .param("email", "bill@paymybuddy.com"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/profile"))
                .andExpect(redirectedUrl("/profile"))
                .andReturn();
    }



    @Test
    @WithMockUser(username = "test@paymybuddy.com")
    void deleteContactIT() throws Exception {

        mockMvc.perform(delete("/profile/deleteContact")
                        .param("email", "bill@paymybuddy.com"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/profile"))
                .andExpect(redirectedUrl("/profile"))
                .andReturn();
    }
}
