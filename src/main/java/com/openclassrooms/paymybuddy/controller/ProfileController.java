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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.awt.*;
import java.util.Set;

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
    public String profileVariables(Model model) {

        User loggedUser = userService.getPrincipal();
        model.addAttribute("bankName", loggedUser.getBankName());
        model.addAttribute("accountNumber", loggedUser.getAccountNumber());
        model.addAttribute("contacts", loggedUser.getContacts());
        return "profile";
    }

    @PostMapping("/profile/addAccount")
    public String addAccount(@ModelAttribute ("accountDto") AccountDto accountDto) {
        accountService.addBankAccount(accountDto);
        return "redirect:/profile";
    }

    @PostMapping("/profile/deleteAccount")
    public String deleteAccount() {
        accountService.deleteBankAccount();
        return "redirect:/profile";
    }

    @PostMapping("/profile/addContact")
    public String addContact(@ModelAttribute ("contactDto") ContactDto contactDto) {
        contactService.addContact(contactDto);
        return "redirect:/profile";
    }

    @PostMapping("/profile/deleteContact/{email}")
    public String deleteContact(@PathVariable String email) {
        contactService.deleteContact(email);
        return "redirect:/profile";
    }


}