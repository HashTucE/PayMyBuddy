package com.openclassrooms.paymybuddy.controller;

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

@ContextConfiguration(classes = {LoginController.class})
@ExtendWith(SpringExtension.class)
class LoginControllerTest {

    @Autowired
    private LoginController loginController;

    @MockBean
    private UserService userService;



    @Test
    void testViewRegister() throws Exception {

        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders
                .formLogin();

        MockMvcBuilders.standaloneSetup(loginController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }



    @Test
    void testRegisterView() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/register");

        MockMvcBuilders.standaloneSetup(loginController)
                .build()
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register"));
    }



    @Test
    void testViewlogin() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");

        MockMvcBuilders.standaloneSetup(loginController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("login"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("login"));
    }
}

