package com.openclassrooms.paymybuddy.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Autowired
    private LoginController loginController;

    @Mock
    private ModelAndView modelAndView;



//    @Test
//    void viewLoginTest() {
//
//        //given
//
//        //when
//        doNothing().when(modelAndView).setViewName(anyString());
//        loginController.viewlogin();
//
//        //then
//        verify(modelAndView, times(1)).setViewName(anyString());
//    }
}

