package com.openclassrooms.paymybuddy.integration;

import com.openclassrooms.paymybuddy.dto.BankDto;
import com.openclassrooms.paymybuddy.dto.TransactionDto;
import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import com.openclassrooms.paymybuddy.service.TransactionService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionIT {


    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionService transactionService;



    private User userIT;
    private int id;



    @BeforeEach
    void createUser() {
        userIT = new User("userIT@test.fr", "pass", BigDecimal.ZERO,"name", "number");
        userRepository.save(userIT);
        id = userIT.getUserId();
    }

    @AfterEach
    void deleteUser() {
        userRepository.deleteById(id);
    }





    @Test
    @DisplayName("Should add money to user's balance")
    @WithMockUser("userIT@test.fr")
    void depositIT() {

        //given
        BankDto amount = new BankDto(BigDecimal.valueOf(50));

        //when
        transactionService.deposit(amount);

        //then
        assertEquals(BigDecimal.valueOf(50), userIT.getBalance());
    }



    @Test
    @DisplayName("Should subtract money to user's balance")
    @WithMockUser("userIT@test.fr")
    void withdrawIT() {

        //given
        userIT.setBalance(BigDecimal.valueOf(100));
        BankDto amount = new BankDto(BigDecimal.valueOf(50));

        //when
        transactionService.withdraw(amount);

        //then
        assertEquals(BigDecimal.valueOf(50), userIT.getBalance());
    }



    @Test
    @DisplayName("Should make a transfer from TransactionDto")
    @WithMockUser("userIT@test.fr")
    void transferToBuddyIT() {

        //given
        userIT.setBalance(BigDecimal.valueOf(1005));

        User beneficiary = new User("beneficiaryIT@test.fr", "pass", "name", "number");
        userRepository.save(beneficiary);
        int id2 = beneficiary.getUserId();

        User payMyBuddy = userService.findByEmail("bill@paymybuddy.com");

        TransactionDto transactionDto = new TransactionDto("beneficiaryIT@test.fr", BigDecimal.valueOf(1000), "test");

        //when
        transactionService.makeTransaction(transactionDto);

        //then
        assertEquals(BigDecimal.ZERO, userIT.getBalance());
        assertEquals(BigDecimal.valueOf(1000), beneficiary.getBalance());
        assertEquals(BigDecimal.valueOf(5), payMyBuddy.getBalance());

        userRepository.deleteById(id2);

        //what about bill@paymybuddy ???
    }



}
