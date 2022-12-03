package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.dto.AccountDto;
import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.exceptions.NoBankAccountException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//@Transactional
public class AccountService {

    @Autowired
    private UserService userService;



    private static final Logger log = LogManager.getLogger(AccountService.class);


    /**
     * Add or update the bank account from the logged user
     * @param accountDto containing bank name and account number
     */
    public void addBankAccount(AccountDto accountDto) {

        User loggedUser = userService.getPrincipal();

        if(!accountDto.getBankName().isBlank() && !accountDto.getAccountNumber().isBlank()) {
            loggedUser.setBankName(accountDto.getBankName());
            loggedUser.setAccountNumber(accountDto.getAccountNumber());
            userService.save(loggedUser);
            log.info("Bank account successfully added/updated for " + loggedUser);
        } else {
            log.error("Bank account not correctly set for " + loggedUser.getEmail());
            throw new NoBankAccountException("Bank account not correctly set for " + loggedUser.getEmail());
        }
    }


    /**
     * Set bank account attribute to null
     */
    public void deleteBankAccount() {

        User loggedUser = userService.getPrincipal();


        loggedUser.setBankName(null);
        loggedUser.setAccountNumber(null);
        userService.save(loggedUser);
        log.info("Bank account successfully deleted for " + loggedUser);
    }
}
