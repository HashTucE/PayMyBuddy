package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NavigationController {


    @Autowired
    UserService userService;



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
        return mav;
    }




    @GetMapping("/contact")
    public ModelAndView viewContact() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("contact");
        return modelAndView;
    }



    @GetMapping("/forgotPassword")
    public ModelAndView getForgetPassword() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("forgotPassword");
        return modelAndView;
    }

}
