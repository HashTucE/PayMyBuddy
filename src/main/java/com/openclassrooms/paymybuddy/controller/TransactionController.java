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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView viewTransfer() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/transfer/page/1");

        log.info("request get viewTransfer");
        return mav;
    }



    @GetMapping("/transfer/page/{pageNumber}")
    public ModelAndView getOnePage(@PathVariable("pageNumber") int currentPage){

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/transfer");

        User loggedUser = userService.getPrincipal();
        mav.addObject("contacts", loggedUser.getContacts());
        mav.addObject("balance", loggedUser.getBalance());

        Page<TransactionDto> page = transactionService.getPageTransactionDto(currentPage);
        List<TransactionDto> transactions = page.getContent();

        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());

        mav.addObject("pageNumbers", pageNumbers);
        mav.addObject("currentPage", currentPage);
        mav.addObject("totalItems", totalItems);
        mav.addObject("totalPages", totalPages);
        mav.addObject("transactions", transactions);

        return mav;
    }



    @PostMapping("/transfer/fromBank")
    public ModelAndView receiveFromBank(@ModelAttribute("bankDto") BankDto bankDto) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("bankDto", new BankDto());
        mav.setViewName("redirect:/transfer");
        transactionService.deposit(bankDto);

        log.info("request post receiveFromBank");
        return mav;
    }


    @PostMapping("/transfer/toBank")
    public ModelAndView sendToBank(@ModelAttribute ("bankDto") BankDto bankDto) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("bankDto", new BankDto());
        mav.setViewName("redirect:/transfer");
        transactionService.withdraw(bankDto);

        log.info("request post sendToBank");
        return mav;
    }


    @PostMapping("/transfer/toBuddy")
    public ModelAndView sendToBuddy(@ModelAttribute ("transaction") TransactionDto transactionDto) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("transactionDto", new TransactionDto());
        mav.setViewName("redirect:/transfer");
        transactionService.makeTransaction(transactionDto);

        log.info("request post sendToBuddy");
        return mav;
    }

}
