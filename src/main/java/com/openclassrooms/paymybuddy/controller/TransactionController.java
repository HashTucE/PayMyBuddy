package com.openclassrooms.paymybuddy.controller;


import com.openclassrooms.paymybuddy.dto.BankDto;
import com.openclassrooms.paymybuddy.dto.TransactionDto;
import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.service.TransactionService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public String viewTransfer(Model model) {

        log.info("request get viewTransfer into Transaction controller");
        return "redirect:/transfer/page/1";
    }



    @GetMapping("/transfer/page/{pageNumber}")
    public String getOnePage(Model model, @PathVariable("pageNumber") int currentPage){

        User loggedUser = userService.getPrincipal();
        model.addAttribute("contacts", loggedUser.getContacts());
        model.addAttribute("balance", loggedUser.getBalance());

        Page<TransactionDto> page = transactionService.getPageTransactionDto(currentPage);
        List<TransactionDto> transactions = page.getContent();

        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("transactions", transactions);

        return "/transfer";
    }



    @PostMapping("/transfer/fromBank")
    public String receiveFromBank(@ModelAttribute("bankDto") BankDto bankDto) {
        transactionService.deposit(bankDto);

        log.info("request post receiveFromBank into Transaction controller");
        return "redirect:/transfer";
    }


    @PostMapping("/transfer/toBank")
    public String sendToBank(@ModelAttribute ("bankDto") BankDto bankDto) {
        transactionService.withdraw(bankDto);

        log.info("request post sendToBank into Transaction controller");
        return "redirect:/transfer";
    }


    @PostMapping("/transfer/toBuddy")
    public String sendToBuddy(@ModelAttribute ("transaction") TransactionDto transactionDto) {
        transactionService.makeTransaction(transactionDto);

        log.info("request post sendToBuddy into Transaction controller");
        return "redirect:/transfer";
    }

}
