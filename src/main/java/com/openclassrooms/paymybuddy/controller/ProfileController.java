package com.openclassrooms.paymybuddy.controller;


import com.openclassrooms.paymybuddy.dto.AccountDto;
import com.openclassrooms.paymybuddy.dto.ContactDto;
import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.service.AccountService;
import com.openclassrooms.paymybuddy.service.ContactService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileController {



    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ContactService contactService;


    private static final Logger log = LogManager.getLogger(ProfileController.class);


    @ModelAttribute
    AccountDto accountDto () {
        return new AccountDto();
    }

    @ModelAttribute
    ContactDto contactDto () {
        return new ContactDto();
    }



    @GetMapping("/profile")
    public ModelAndView viewProfile() {

        User loggedUser = userService.getPrincipal();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("profile");
        mav.addObject("bankName", loggedUser.getBankName());
        mav.addObject("accountNumber", loggedUser.getAccountNumber());
        mav.addObject("contacts", loggedUser.getContacts());

        log.info("request get profile");
        return mav;
    }



    @PostMapping("/profile/addAccount")
    public ModelAndView addAccount(@ModelAttribute ("accountDto") AccountDto accountDto) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("accountDto", new AccountDto());
        mav.setViewName("redirect:/profile");
        accountService.addBankAccount(accountDto);

        log.info("returning register view");
        return mav;
    }



    @DeleteMapping("/profile/deleteAccount")
    public ModelAndView deleteAccount() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/profile");
        accountService.deleteBankAccount();

        log.info("request post deleteAccount");
        return mav;
    }



    @PostMapping("/profile/addContact")
    public ModelAndView addContact(@ModelAttribute ("contactDto") ContactDto contactDto) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("contactDto", new ContactDto());
        mav.setViewName("redirect:/profile");
        contactService.addContact(contactDto);

        log.info("request post addContact");
        return mav;
    }




    @DeleteMapping("/profile/deleteContact/{email}")
    public ModelAndView deleteContact(@PathVariable String email) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/profile");
        contactService.deleteContact(email);

        log.info("request post deleteContact");
        return mav;
    }
}
