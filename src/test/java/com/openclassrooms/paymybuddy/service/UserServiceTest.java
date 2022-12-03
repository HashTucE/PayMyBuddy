package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.exceptions.InvalidEmailException;
import com.openclassrooms.paymybuddy.exceptions.UserAlreadyExistException;
import com.openclassrooms.paymybuddy.exceptions.UserNotExistException;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserService.class, BCryptPasswordEncoder.class})
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @MockBean
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Spy
    @InjectMocks
    private UserService userService;


    @Test
    @DisplayName("Should call save method")
    void savePositiveTest() {

        //given
        User user = new User("test@test.fr", "pass");
        //when
        userService.save(user);

        //then
        verify(userRepository, times(1)).save(user);
    }


    @Test
    @DisplayName("Should call findByMail from repository")
    void findByMailPositiveTest() {

        //given
        User user = new User("test@test.fr", "pass");

        //when
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        userService.findByEmail("test@test.fr");

        //then
        verify(userRepository, times(1)).findByEmail(anyString());
    }


    @Test
    @DisplayName("Should call save method for create user")
    void createUserPositiveTest() {

        //given
        UserDto userDto = new UserDto("test@test.fr", "pass");

        //when
        doReturn(true).when(userService).isValidEmailAddress(anyString());
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(anyString());
        userService.createUser(userDto);

        //then
        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    @DisplayName("Should throw an exception when the email is invalid")
    void createUserNegativeTest1() {

        //given
        UserDto userDto = new UserDto();
        userDto.setEmail("invalidEmail");
        userDto.setPassword("password");

        //when
        doReturn(false).when(userService).isValidEmailAddress(anyString());

        //then
        assertThrows(InvalidEmailException.class, () -> userService.createUser(userDto));
    }


    @Test
    @DisplayName("Should throw an exception when the email is already taken")
    void createUserNegativeTest2() {

        //given
        UserDto userDto = new UserDto();
        userDto.setEmail("test@test.com");
        userDto.setPassword("test");

        //when
        doReturn(true).when(userService).isValidEmailAddress(anyString());
        when(userRepository.findByEmail(anyString())).thenReturn(new User());

        //then
        assertThrows(UserAlreadyExistException.class, () -> userService.createUser(userDto));
    }


    @Test
    @DisplayName("Should return false when the email is invalid")
    void isValidEmailAddressPositiveTest() {

        //given//when
        boolean valid = userService.isValidEmailAddress("test");

        //then
        assertFalse(valid);
    }


    @Test
    @DisplayName("Should return true when the email is valid")
    void isValidEmailAddressNegativeTest() {

        //given//when
        boolean valid = userService.isValidEmailAddress("test@test.fr");

        //then
        assertTrue(valid);
    }




//    @Test
//    @WithMockUser(username = "user@test.fr")
//    @DisplayName("Should return the logged user")
//    void getPrincipalPositiveTest() {
//
//        SecurityContext securityContext = new SecurityContextImpl();
//        securityContext.setAuthentication(new TestingAuthenticationToken("user@test.fr", "pass", "ROLE_USER"));
//        SecurityContextHolder.setContext(securityContext);
//
//        //given
//        String expected = "user@test.fr";
//
//        //when
//        User principal = userService.getPrincipal();
//
//        //then
//        assertEquals(expected, principal.getEmail());
//    }


}