package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.dto.AccountDto;
import com.openclassrooms.paymybuddy.dto.ContactDto;
import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.service.AccountService;
import com.openclassrooms.paymybuddy.service.ContactService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {


    @InjectMocks
    private ProfileController profileController;

    @Mock
    private UserService userService;

    @Mock
    private AccountService accountService;

    @Mock
    private ContactService contactService;



    @Test
    void accountDtoTest() {assertThat(profileController.accountDto()).isInstanceOf(AccountDto.class);}

    @Test
    void contactDtoTest() {assertThat(profileController.contactDto()).isInstanceOf(ContactDto.class);}


    @Test
    void viewProfileTest() {

        //given
        User user = new User("user@test.fr", "pass", BigDecimal.valueOf(100), "name", "number");
        User contact = new User("contact@test.fr", "pass");
        Set<User> contacts = new HashSet<>();
        contacts.add(contact);
        user.setContacts(contacts);

        //when
        when(userService.getPrincipal()).thenReturn(user);
        ModelAndView mav = profileController.viewProfile();

        //then
        assertEquals("profile", mav.getViewName());
        assertEquals("name", mav.getModel().get("bankName"));
        assertEquals("number", mav.getModel().get("accountNumber"));
        assertEquals(user.getContacts(), mav.getModel().get("contacts"));
        assertEquals(3, mav.getModel().size());
    }




    @Test
    void addAccountTest() {

        //given
        AccountDto accountDto = new AccountDto("name", "number");

        //when
        doNothing().when(accountService).addBankAccount(any(AccountDto.class));
        ModelAndView mav = profileController.addAccount(accountDto);

        //then
        verify(accountService, times(1)).addBankAccount(any(AccountDto.class));
        assertEquals("redirect:/profile", mav.getViewName());
        assertTrue(mav.getModel().containsKey("accountDto"));
        assertEquals(1, mav.getModel().size());
    }



    @Test
    void deleteAccountTest() {

        //given
        String expected = "redirect:/profile";

        //when
        doNothing().when(accountService).deleteBankAccount();
        ModelAndView mav = profileController.deleteAccount();

        //then
        verify(accountService, times(1)).deleteBankAccount();
        assertEquals(expected, mav.getViewName());
    }







    @Test
    void addContactTest() {

        //given
        ContactDto contactDto = new ContactDto("contact@test.fr");

        //when
        doNothing().when(contactService).addContact(any(ContactDto.class));
        ModelAndView mav = profileController.addContact(contactDto);

        //then
        verify(contactService, times(1)).addContact(any(ContactDto.class));
        assertEquals("redirect:/profile", mav.getViewName());
        assertTrue(mav.getModel().containsKey("contactDto"));
        assertEquals(1, mav.getModel().size());
    }


    @Test
    void deleteContactTest() {

        //given//when
        doNothing().when(contactService).deleteContact(anyString());
        ModelAndView mav = profileController.deleteContact(anyString());

        //then
        verify(contactService, times(1)).deleteContact(anyString());
        assertEquals("redirect:/profile", mav.getViewName());
        assertEquals(0, mav.getModel().size());
    }
}

