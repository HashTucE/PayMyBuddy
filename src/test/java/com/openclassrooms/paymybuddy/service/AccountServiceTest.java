package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.dto.AccountDto;
import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.exceptions.NoBankAccountException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {




    @Mock
    private UserService userService;


    @InjectMocks
    private AccountService accountService;




    @Test
    @DisplayName("Should add the bank account to the user")
    void addBankAccountPositiveTest() {

        //given
        User user = new User("user@gmail.com", "password");
        AccountDto accountDto = new AccountDto("name", "number");

        //when
        when(userService.getPrincipal()).thenReturn(user);
        accountService.addBankAccount(accountDto);

        //then
        verify(userService, times(1)).save(user);
        assertEquals(user.getBankName(), accountDto.getBankName());
        assertEquals(user.getAccountNumber(), accountDto.getAccountNumber());
    }


    @Test
    @DisplayName("Should not add the bank account because there is no bank name")
    void addBankAccountNegativeTest() {

        //given
        User user = new User("user@gmail.com", "password");
        AccountDto accountDto = new AccountDto(" ", "number");

        //when
        when(userService.getPrincipal()).thenReturn(user);

        //then
        assertThrows(NoBankAccountException.class, () -> accountService.addBankAccount(accountDto));
    }


    @Test
    @DisplayName("Should not add the bank account because there is no account number")
    void addBankAccountNegativeTest2() {

        //given
        User user = new User("user@gmail.com", "password");
        AccountDto accountDto = new AccountDto("name", " ");

        //when
        when(userService.getPrincipal()).thenReturn(user);

        //then
        assertThrows(NoBankAccountException.class, () -> accountService.addBankAccount(accountDto));
    }


    @Test
    @DisplayName("Should not add the bank account because there is no bank account")
    void addBankAccountNegativeTest3() {

        //given
        User user = new User("user@gmail.com", "password");
        AccountDto accountDto = new AccountDto(" ", " ");

        //when
        when(userService.getPrincipal()).thenReturn(user);

        //then
        assertThrows(NoBankAccountException.class, () -> accountService.addBankAccount(accountDto));
    }




    @Test
    @DisplayName("Should set bank account attributes to null")
    void deleteBankAccountPositiveTest() {

        //given
        User user = new User("user@gmail.com", "password", "name", "number");

        //when
        when(userService.getPrincipal()).thenReturn(user);
        accountService.deleteBankAccount();

        //then
        verify(userService, times(1)).save(user);
        assertNull(user.getBankName());
        assertNull(user.getAccountNumber());
    }







}
