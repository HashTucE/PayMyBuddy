package com.openclassrooms.paymybuddy.service;


import com.openclassrooms.paymybuddy.dto.ContactDto;
import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.exceptions.AlreadyBuddyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {


    @Mock
    private UserService userService;

    @InjectMocks
    private ContactService contactService;



    @Test
    @DisplayName("should return true if already exist")
    public void isContactAlreadyExistPositiveTest() {

        //given
        User user = new User("user@gmail.com", "password");
        User contact = new User("contact@gmail.com", "password");
        Set<User> contactList = new HashSet<>();
        contactList.add(contact);
        user.setContacts(contactList);

        //when
        when(userService.getPrincipal()).thenReturn(user);
        boolean exist = contactService.isContactAlreadyExist("contact@gmail.com");

        //then
        assertTrue(exist);
    }


    @Test
    @DisplayName("should return false if not exisiting")
    public void isContactAlreadyExistNegativeTest() {

        //given
        User user = new User("user@gmail.com", "password");
        User contact = new User("contact@gmail.com", "password");
        Set<User> contactList = new HashSet<>();
        contactList.add(contact);
        user.setContacts(contactList);

        //when
        when(userService.getPrincipal()).thenReturn(user);
        boolean exist = contactService.isContactAlreadyExist("other@gmail.com");

        //then
        assertFalse(exist);
    }



    @Test
    @DisplayName("Should add the contact to the user")
    void addContactPositiveTest() {

        //given
        User user = new User("user@gmail.com", "password");
        User contact = new User("contact@gmail.com", "password");
        ContactDto contactDto = new ContactDto(contact.getEmail());

        //when
        when(userService.getPrincipal()).thenReturn(user);
        when(userService.findByEmail(anyString())).thenReturn(contact);
        contactService.addContact(contactDto);

        //then
        assertTrue(user.getContacts().contains(contact));
    }



    @Test
    @DisplayName("Should return an exception when contact already added")
    void addContactNegativeTest() {

        //given
        User user = new User("user@gmail.com", "password");
        User contact = new User("contact@gmail.com", "password");
        Set<User> contactList = new HashSet<>();
        contactList.add(contact);
        user.setContacts(contactList);

        ContactDto contactDto = new ContactDto(contact.getEmail());

        //when
        when(userService.getPrincipal()).thenReturn(user);
        when(userService.findByEmail(anyString())).thenReturn(contact);

        //then
        assertThrows(AlreadyBuddyException.class, () -> contactService.addContact(contactDto));
    }




    @Test
    @DisplayName("Should remove the contact from the user")
    void removeContactPositiveTest() {

        //given
        User user = new User("user@gmail.com", "password");
        User contact = new User("contact@gmail.com", "password");
        user.getContacts().add(contact);

        //when
        when(userService.getPrincipal()).thenReturn(user);
        when(userService.findByEmail(anyString())).thenReturn(contact);
        contactService.deleteContact(contact.getEmail());

        //then
        assertFalse(user.getContacts().contains(contact));
    }
}