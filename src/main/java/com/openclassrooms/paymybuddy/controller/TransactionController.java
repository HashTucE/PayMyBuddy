package com.openclassrooms.paymybuddy.controller;


import com.openclassrooms.paymybuddy.dto.BankDto;
import com.openclassrooms.paymybuddy.dto.TransactionDto;
import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.service.TransactionService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TransactionController {



    @Autowired
    private UserService userService;



    @Autowired
    private TransactionService transactionService;




    private static final Logger log = LogManager.getLogger(TransactionController.class);



    @ModelAttribute
    BankDto bankDto () {
        return new BankDto();
    }

    @ModelAttribute
    TransactionDto transactionDto () {
        return new TransactionDto();
    }



    @GetMapping("/transfer")
    public String profileVariables(Model model) {
        User loggedUser = userService.getPrincipal();
        model.addAttribute("contacts", loggedUser.getContacts());
        model.addAttribute("transactions", transactionService.getSortedList(transactionService.getCustomList()));
        model.addAttribute("balance", loggedUser.getBalance());
        return "transfer";
    }


    @PostMapping("/transfer/fromBank")
    public String receiveFromBank(@ModelAttribute("bankDto") BankDto bankDto) {
        transactionService.deposit(bankDto);
        return "redirect:/transfer";
    }


    @PostMapping("/transfer/toBank")
    public String sendToBank(@ModelAttribute ("bankDto") BankDto bankDto) {
        transactionService.withdraw(bankDto);
        return "redirect:/transfer";
    }


    @PostMapping("/transfer/toBuddy")
    public String sendToBuddy(@ModelAttribute ("transaction") TransactionDto transactionDto) {
        transactionService.makeTransaction(transactionDto);
        return "redirect:/transfer";
    }

}
