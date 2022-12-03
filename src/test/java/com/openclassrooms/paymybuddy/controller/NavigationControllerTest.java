package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class NavigationControllerTest {

    @InjectMocks
    private NavigationController navigationController;

    @Mock
    private UserService userService;



    @Test
    void viewHomeTest() {

        //given
        User user = new User("user@test.fr", "pass", BigDecimal.valueOf(100), "name", "number");
        user.setDebitList(new ArrayList<>());

        //when
        when(userService.getPrincipal()).thenReturn(user);
        ModelAndView mav = navigationController.viewHome();

        //then
        assertEquals("home", mav.getViewName());
        assertEquals("user@test.fr", mav.getModel().get("principal"));
        assertEquals(BigDecimal.valueOf(100), mav.getModel().get("balance"));
        assertEquals("name", mav.getModel().get("bankName"));
        assertEquals("number", mav.getModel().get("accountNumber"));
        assertEquals(user.getDebitList(), mav.getModel().get("transactions"));
    }



    @Test
    void viewContactTest() {

        //given//when
        ModelAndView mav = navigationController.viewContact();

        //then
        assertEquals("contact", mav.getViewName());
    }



    @Test
    void getForgotPasswordTest() {

        //given//when
        ModelAndView mav = navigationController.getForgetPassword();

        //then
        assertEquals("forgotPassword", mav.getViewName());
    }


}

