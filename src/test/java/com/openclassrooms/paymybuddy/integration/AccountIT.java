package com.openclassrooms.paymybuddy.integration;

import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AccountIT {

    @Autowired
    private UserRepository userRepository;

//    @Test
//    void addBankAccountTest() {
//        //GIVEN
//        User user = userRepository.findByEmail("test2@paymybuddy.com");
//        //WHEN
//        Account account = new Account("CIC", "FRTOTO");
//
//        user.addBankAccount(account);
//        userRepository.save(user);
//        //THEN
//    }
//
//
//    @Test
//    void removeBankAccountTest() {
//        //GIVEN
//        User user = userRepository.findByEmail("test2@paymybuddy.com");
//        //WHEN
//        Account account = new Account("CIC", "FRTOTO");
//
//        user.removeBankAccount(account);
//        userRepository.save(user);
//        //THEN
//    }
}
