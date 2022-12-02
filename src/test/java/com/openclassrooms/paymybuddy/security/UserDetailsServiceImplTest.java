package com.openclassrooms.paymybuddy.security;

import com.openclassrooms.paymybuddy.entity.Transaction;
import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.exceptions.InsufficientFundException;
import com.openclassrooms.paymybuddy.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserDetailsServiceImpl.class})
@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {


    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private UserService userService;



    @Test
    @DisplayName("should throw an exception if username not exist")
    public void loadUserByUsernameNegativeTest2() {

        //when
        when(userService.findByEmail(anyString())).thenReturn(null);

        //then
        assertThrows(UsernameNotFoundException.class, () -> userDetailsServiceImpl.loadUserByUsername(anyString()));
    }


    @Test
    @DisplayName("should load a valid user")
    void testLoadUserByUsername() throws UsernameNotFoundException {

        //given
        User user = new User("test@test.fr", "pass");

        //when
        when(userService.findByEmail(any())).thenReturn(user);
        UserDetails result = userDetailsServiceImpl.loadUserByUsername("test@test.fr");

        //then
        assertEquals(1, result.getAuthorities().size());
        assertTrue(result.isEnabled());
        assertTrue(result.isCredentialsNonExpired());
        assertTrue(result.isAccountNonLocked());
        assertTrue(result.isAccountNonExpired());
        assertEquals("test@test.fr", result.getUsername());
        assertEquals("pass", result.getPassword());
        verify(userService, times(1)).findByEmail(any());
    }
}

