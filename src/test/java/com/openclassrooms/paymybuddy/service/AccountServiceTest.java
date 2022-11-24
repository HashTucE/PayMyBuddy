package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.dto.AccountDto;
import com.openclassrooms.paymybuddy.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        assertTrue(user.getBankName().equals(accountDto.getBankName()));
        assertTrue(user.getAccountNumber().equals(accountDto.getAccountNumber()));
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
