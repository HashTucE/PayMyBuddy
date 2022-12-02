package com.openclassrooms.paymybuddy.service;


import com.openclassrooms.paymybuddy.dto.ContactDto;
import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.exceptions.AlreadyBuddyException;
import com.openclassrooms.paymybuddy.exceptions.UserNotExistException;
import com.openclassrooms.paymybuddy.exceptions.YourselfException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class ContactService {



    @Autowired
    private UserService userService;


    private static final Logger log = LogManager.getLogger(ContactService.class);



    /**
     * return true if contact already exist
     * @param email contact to check
     * @return true if already exist
     */
    public boolean isContactAlreadyExist(String email) {

        User loggedUser = userService.getPrincipal();

        boolean alreadyExist = false;
        for(User contact : loggedUser.getContacts()) {
            if (contact.getEmail().equals(email)) {
                alreadyExist = true;
            }
        }
        return alreadyExist;
    }



    /**
     * add a new contact to the list
     * @param contactDto contactDto
     */
    public void addContact(ContactDto contactDto) {

        User loggedUser = userService.getPrincipal();
        User contactUser = userService.findByEmail(contactDto.getEmail());
        boolean alreadyExist = isContactAlreadyExist(contactDto.getEmail());

        if (alreadyExist) {
            log.error(contactDto.getEmail() + " is already contact's " + loggedUser.getEmail());
            throw new AlreadyBuddyException(contactDto.getEmail() + " is already contact's " + loggedUser.getEmail());
        } else if (contactDto.getEmail().equals(loggedUser.getEmail())) {
            log.error("logged user can't add his own email");
            throw new YourselfException("This is your own email !");
        } else if (contactUser == null) {
            log.error("user not existing");
            throw new UserNotExistException("User not existing !");
        } else {
            loggedUser.getContacts().add(contactUser);
            userService.save(loggedUser);
            log.info("Contact " + contactDto.getEmail() + " successfully added from " + loggedUser.getEmail());
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

        loggedUser.getContacts().remove(contactUser);
        userService.save(loggedUser);
        log.info("Contact " + email + " successfully deleted from " + loggedUser.getEmail());
    }








}
