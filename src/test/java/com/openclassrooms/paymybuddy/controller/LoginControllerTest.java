package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class LoginControllerTest {


    @InjectMocks
    private LoginController loginController;

    @Mock
    private UserService userService;

    @Mock
    BindingResult result;





    @Test
    void viewLoginTest() {

        //given//when
        ModelAndView mav = loginController.viewlogin();

        //then
        assertEquals("login", mav.getViewName());
    }



    @Test
    void viewRegisterTest() {

        //given//when
        ModelAndView mav = loginController.viewRegister();

        //then
        assertEquals("register", mav.getViewName());
        assertTrue(mav.getModel().containsKey("userDto"));
    }



    @Test
    void createNewUserNegativeTest() {

        //given
        User user = new User("user@test.fr", "pass");
        UserDto userDto = new UserDto("user@test.fr", "pass");

        //when
        when(userService.findByEmail(anyString())).thenReturn(user);
        when(result.hasErrors()).thenReturn(true);
        loginController.createNewUser(userDto, result);

        //then
        verify(result).rejectValue("email", "error.user",
                "There is already a user registered with the user name provided");
    }



    @Test
    void createNewUserNegativeTest2() {

        //given
        UserDto userDto = new UserDto("user@test.fr", "pass");

        //when
        when(userService.findByEmail(anyString())).thenReturn(null);
        when(result.hasErrors()).thenReturn(true);
        ModelAndView mav = loginController.createNewUser(userDto, result);

        //then
        assertEquals("register", mav.getViewName());
    }


    @Test
    void createNewUserPositiveTest() {

        //given
        User user = new User("user@test.fr", "pass");
        UserDto userDto = new UserDto("user@test.fr", "pass");

        //when
        when(userService.findByEmail(anyString())).thenReturn(user);
        when(result.hasErrors()).thenReturn(false);
        when(userService.createUser(userDto)).thenReturn(user);
        ModelAndView mav = loginController.createNewUser(userDto, result);

        //then
        assertEquals("User has been registered successfully", mav.getModel().get("successMessage"));
        assertTrue(mav.getModel().containsKey("user"));
        assertEquals("register", mav.getViewName());
    }

}

