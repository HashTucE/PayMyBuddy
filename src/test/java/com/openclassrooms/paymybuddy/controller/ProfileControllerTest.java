package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.service.AccountService;
import com.openclassrooms.paymybuddy.service.ContactService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ProfileController.class})
@ExtendWith(SpringExtension.class)
class ProfileControllerTest {
    @MockBean
    private AccountService accountService;

    @MockBean
    private ContactService contactService;

    @Autowired
    private ProfileController profileController;

    @MockBean
    private UserService userService;



    @Test
    void testAddAccount() throws Exception {

        doNothing().when(accountService).addBankAccount(any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/profile/addAccount");
        MockMvcBuilders.standaloneSetup(profileController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("accountDto", "contactDto"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/profile"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/profile"));
    }



    @Test
    void testDeleteAccount() throws Exception {

        doNothing().when(accountService).deleteBankAccount();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/profile/deleteAccount");
        MockMvcBuilders.standaloneSetup(profileController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("accountDto", "contactDto"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/profile"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/profile"));
    }



    @Test
    void testAddContact() throws Exception {

        doNothing().when(contactService).addContact(any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/profile/addContact");
        MockMvcBuilders.standaloneSetup(profileController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("accountDto", "contactDto"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/profile"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/profile"));
    }



    @Test
    void testDeleteContact() throws Exception {

        doNothing().when(contactService).deleteContact(any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/profile/deleteContact/{email}",
                "jane.doe@example.org");
        MockMvcBuilders.standaloneSetup(profileController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("accountDto", "contactDto"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/profile"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/profile"));
    }



    @Test
    void testProfileVariables() throws Exception {

        //given
        User user = new User("test@test.fr", "pass", BigDecimal.ZERO);

        //when
        when(userService.getPrincipal()).thenReturn(user);

        //then
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders
                .formLogin();

        MockMvcBuilders.standaloneSetup(profileController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

