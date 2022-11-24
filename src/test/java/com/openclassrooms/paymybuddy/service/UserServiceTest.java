package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.exceptions.InvalidEmailException;
import com.openclassrooms.paymybuddy.exceptions.UserAlreadyExistException;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;




    @Test
    @DisplayName("Should return false when the email is invalid")
    void isValidEmailAddressPositiveTest() {
        boolean valid = userService.isValidEmailAddress("test");
        assertFalse(valid);
    }


    @Test
    @DisplayName("Should return true when the email is valid")
    void isValidEmailAddressNegativeTest() {
        boolean valid = userService.isValidEmailAddress("test@test.fr");
        assertTrue(valid);
    }






    @Test
    @DisplayName("Should throw an exception when the email is invalid")
    void createUserNegativeTest1() {

        UserDto userDto = new UserDto();
        userDto.setEmail("invalidEmail");
        userDto.setPassword("password");

        assertThrows(InvalidEmailException.class, () -> userService.createUser(userDto));
    }

    @Test
    @DisplayName("Should throw an exception when the email is already taken")
    void createUserNegativeTest2() {

        UserDto userDto = new UserDto();
        userDto.setEmail("test@test.com");
        userDto.setPassword("test");

        when(userRepository.findByEmail(anyString())).thenReturn(new User());

        assertThrows(UserAlreadyExistException.class, () -> userService.createUser(userDto));
    }
}