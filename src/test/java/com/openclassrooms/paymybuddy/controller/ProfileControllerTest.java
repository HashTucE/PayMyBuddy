package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ProfileController.class})
@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {


    private ProfileController profileController;

    @Mock
    private Model model;

    @Mock
    private UserService userService;


    @BeforeEach
    public void before() {
        profileController = new ProfileController();
    }

    @Test
    void addContact() {
    }


//    @Test
//    public void viewControllerTest() {
//
//        User user = new User("test@test.fr", "pass", "bank", "number");
//        String returnValue = profileController.viewProfile(model);
//
//        when(userService.getPrincipal()).thenReturn(user);
//        verify(model, times(1)).addAttribute("bankName", user.getBankName());
//        assertEquals("profile", returnValue);
//    }







//    @Test
//    void testAddAccount() throws Exception {
//
//        doNothing().when(accountService).addBankAccount(any());
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/profile/addAccount");
//        MockMvcBuilders.standaloneSetup(profileController)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isFound())
//                .andExpect(MockMvcResultMatchers.model().size(2))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("accountDto", "contactDto"))
//                .andExpect(MockMvcResultMatchers.view().name("redirect:/profile"))
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/profile"));
//    }
//
//
//
//    @Test
//    void testDeleteAccount() throws Exception {
//
//        doNothing().when(accountService).deleteBankAccount();
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/profile/deleteAccount");
//        MockMvcBuilders.standaloneSetup(profileController)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isFound())
//                .andExpect(MockMvcResultMatchers.model().size(2))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("accountDto", "contactDto"))
//                .andExpect(MockMvcResultMatchers.view().name("redirect:/profile"))
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/profile"));
//    }
//
//
//
//    @Test
//    void testAddContact() throws Exception {
//
//        doNothing().when(contactService).addContact(any());
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/profile/addContact");
//        MockMvcBuilders.standaloneSetup(profileController)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isFound())
//                .andExpect(MockMvcResultMatchers.model().size(2))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("accountDto", "contactDto"))
//                .andExpect(MockMvcResultMatchers.view().name("redirect:/profile"))
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/profile"));
//    }
//
//
//
//    @Test
//    void testDeleteContact() throws Exception {
//
//        doNothing().when(contactService).deleteContact(any());
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/profile/deleteContact/{email}",
//                "jane.doe@example.org");
//        MockMvcBuilders.standaloneSetup(profileController)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isFound())
//                .andExpect(MockMvcResultMatchers.model().size(2))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("accountDto", "contactDto"))
//                .andExpect(MockMvcResultMatchers.view().name("redirect:/profile"))
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/profile"));
//    }
//
//
//
//    @Test
//    void testProfileVariables() throws Exception {
//
//
//        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders
//                .formLogin();
//
//        MockMvcBuilders.standaloneSetup(profileController)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//    }




}

