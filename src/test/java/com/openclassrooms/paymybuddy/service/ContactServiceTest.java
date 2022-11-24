package com.openclassrooms.paymybuddy.service;


import com.openclassrooms.paymybuddy.dto.ContactDto;
import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {


    @Mock
    private UserService userService;

    @InjectMocks
    private ContactService contactService;






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