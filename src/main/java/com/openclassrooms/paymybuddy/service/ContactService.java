package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.dto.ContactDto;
import com.openclassrooms.paymybuddy.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ContactService {



    @Autowired
    private UserService userService;


    private static final Logger log = LogManager.getLogger(ContactService.class);


    /**
     * add a new contact to the list
     * @param contactDto contactDto
     */
    public void addContact(ContactDto contactDto) {

        User loggedUser = userService.getPrincipal();
        User contactUser = userService.findByEmail(contactDto.getEmail());

        if (contactUser == null || !loggedUser.getContacts().contains(contactUser)) {
            loggedUser.getContacts().add(contactUser);
            userService.save(loggedUser);
            log.info("Contact " + contactDto.getEmail() + " successfully deleted from " + loggedUser);
        } else {
            log.error("Contact " + contactDto.getEmail() + " does not exist or is already contact's " + loggedUser);
        }
    }


    /**
     * delete a contact from the list
     * @param email email to delete
     */
    @Transactional
    public void deleteContact(String email) {

        User loggedUser = userService.getPrincipal();
        User contactUser = userService.findByEmail(email);

        try {
            loggedUser.getContacts().remove(contactUser);
            userService.save(loggedUser);
            log.info("Contact " + email + " successfully deleted from " + loggedUser);
        } catch (Exception ex) {
            log.error("An exception prevents deleting the contact " + email + " from " + loggedUser);
        }

    }








}
