package com.openclassrooms.paymybuddy.service;


import com.openclassrooms.paymybuddy.repository.TransactionRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;



//    @Test
//    @DisplayName("Should return true when the transaction is successful")
//    void makeTransactionPositiveTest() {
//
//        User loggedUser = new User("test@test.fr", "pass", BigDecimal.valueOf(100));
//        User beneficiary = new User("test2@test.fr", "pass", BigDecimal.valueOf(100));
//        User paymybuddy = new User("test3@test.fr", "pass", BigDecimal.valueOf(100));
//
//        TransactionDto transactionDto = new TransactionDto();
//        transactionDto.setBeneficiaryEmail("test@test.com");
//        transactionDto.setAmount(new BigDecimal("10"));
//        transactionDto.setDescription("test");
//
//        when(userService.getPrincipal()).thenReturn(loggedUser);
//        when(userService.findByEmail(transactionDto.getBeneficiaryEmail())).thenReturn(beneficiary);
//        when(userService.findByEmail("bill@paymybuddy.com")).thenReturn(paymybuddy);
//        when(userRepository.save(any(User.class))).thenReturn(loggedUser).thenReturn(beneficiary).thenReturn(paymybuddy);
//        when(transactionRepository.save(any(Transaction.class))).thenReturn(any(Transaction.class));
//
//        boolean result = transactionService.makeTransaction(transactionDto);
//        assertTrue(result);
//    }
//
//    @Test
//    @DisplayName("Should throw an exception when the user has not enough money")
//    void makeTransactionNegativeTest() {
//
//        TransactionDto transactionDto = new TransactionDto();
//        transactionDto.setBeneficiaryEmail("test@test.com");
//        transactionDto.setAmount(new BigDecimal("100"));
//        transactionDto.setDescription("test");
//
//        assertThrows(InsufficientFundException.class, () -> transactionService.makeTransaction(transactionDto));
//    }





    @Test
    @DisplayName("Should return the final amount with tax included")
    void getFinalAmountPositiveTest() {

        BigDecimal amount = new BigDecimal("100");
        BigDecimal expectedAmount = new BigDecimal("105.00");

        assertEquals(expectedAmount, transactionService.getFinalAmount(amount));
    }



//    @Test
//    @DisplayName("Should add money to wallet")
//    void depositNegativeTest() {
//
//        BankDto bankDto = new BankDto(BigDecimal.valueOf(100));
//        User loggedUser = new User("test@test.fr", "pass", BigDecimal.valueOf(100));
//        BigDecimal expected = BigDecimal.valueOf(200);
//
//        when(userService.getPrincipal()).thenReturn(loggedUser);
//
//        assertThrows(NoBankAccountException.class, () -> transactionService.deposit(bankDto));
//
//
//
//
//    }







}