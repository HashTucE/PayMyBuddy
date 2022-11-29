package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NavigationController {


    @Autowired
    UserService userService;

    private static final Logger log = LogManager.getLogger(NavigationController.class);



    @GetMapping("/home")
    public ModelAndView viewHome() {
        User loggedUser = userService.getPrincipal();

        ModelAndView mav = new ModelAndView();
        mav.setViewName("home");
        mav.addObject("principal", userService.getPrincipal().getEmail());
        mav.addObject("balance", loggedUser.getBalance());
        mav.addObject("bankName", loggedUser.getBankName());
        mav.addObject("accountNumber", loggedUser.getAccountNumber());
        mav.addObject("transactions", loggedUser.getDebitList());

        log.info("request get viewHome into Navigation controller");
        return mav;
    }


    @GetMapping("/contact")
    public ModelAndView viewContact() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("contact");

        log.info("request get viewContact into Navigation controller");
        return modelAndView;
    }


    @GetMapping("/forgotPassword")
    public ModelAndView getForgetPassword() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("forgotPassword");

        log.info("request get forgetPassword into Navigation controller");
        return modelAndView;
    }

}
