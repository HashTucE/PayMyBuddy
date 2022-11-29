package com.openclassrooms.paymybuddy.service;


import com.openclassrooms.paymybuddy.dto.BankDto;
import com.openclassrooms.paymybuddy.dto.TransactionDto;
import com.openclassrooms.paymybuddy.entity.Transaction;
import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.exceptions.InsufficientFundException;
import com.openclassrooms.paymybuddy.exceptions.NoBankAccountException;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {TransactionService.class})
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Spy
    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;




    @Test
    @DisplayName("Should return the final amount with tax included")
    void getFinalAmountPositiveTest() {

        BigDecimal amount = new BigDecimal("100");
        BigDecimal expectedAmount = new BigDecimal("100.50");

        assertEquals(expectedAmount, transactionService.getFinalAmount(amount));
    }



    @Test
    @DisplayName("Should return true when the transaction is successful")
    void makeTransactionPositiveTest() {

        //given
        User loggedUser = new User("test@test.fr", "pass", BigDecimal.valueOf(1005));
        User beneficiary = new User("test2@test.fr", "pass", BigDecimal.ZERO);
        User paymybuddy = new User("test3@test.fr", "pass", BigDecimal.ZERO);

        TransactionDto transactionDto = new TransactionDto
                  ("test2@test.fr", new BigDecimal("1000"), "desc");

        //when
        when(userService.getPrincipal()).thenReturn(loggedUser);
        when(userService.findByEmail(transactionDto.getEmail())).thenReturn(beneficiary);
        when(userService.findByEmail("bill@paymybuddy.com")).thenReturn(paymybuddy);
        when(userRepository.save(any(User.class))).thenReturn(loggedUser).thenReturn(beneficiary).thenReturn(paymybuddy);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(any(Transaction.class));
        transactionService.makeTransaction(transactionDto);

        //then
        assertEquals(loggedUser.getBalance(), BigDecimal.ZERO.setScale(2, RoundingMode.HALF_DOWN));
        assertEquals(beneficiary.getBalance(), BigDecimal.valueOf(1000));
        assertEquals(paymybuddy.getBalance(), BigDecimal.valueOf(5).setScale(2, RoundingMode.HALF_DOWN));
    }



    @Test
    @DisplayName("Should throw an exception when the user has not enough money")
    void makeTransactionNegativeTest() {

        //given
        User loggedUser = new User("test@test.fr", "pass", BigDecimal.valueOf(800));
        User beneficiary = new User("test2@test.fr", "pass", BigDecimal.ZERO);
        User paymybuddy = new User("test3@test.fr", "pass", BigDecimal.ZERO);

        TransactionDto transactionDto = new TransactionDto
                ("test2@test.fr", new BigDecimal("1000"), "desc");

        //when
        when(userService.getPrincipal()).thenReturn(loggedUser);
        when(userService.findByEmail(transactionDto.getEmail())).thenReturn(beneficiary);
        when(userService.findByEmail("bill@paymybuddy.com")).thenReturn(paymybuddy);

        //then
        assertThrows(InsufficientFundException.class, () -> transactionService.makeTransaction(transactionDto));
    }



    @Test
    @DisplayName("Should add money to wallet")
    void depositPositiveTest() {

        //given
        BankDto bankDto = new BankDto(BigDecimal.valueOf(100));
        User loggedUser = new User("test@test.fr", "pass", BigDecimal.ZERO, "name", "number");
        BigDecimal expectedBalance = BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_DOWN);

        //when
        when(userService.getPrincipal()).thenReturn(loggedUser);
        transactionService.deposit(bankDto);

        //then
        verify(userRepository, times(1)).save(loggedUser);
        assertEquals(expectedBalance, loggedUser.getBalance());
    }


    @Test
    @DisplayName("Should throw exception when no bank account set")
    void depositNegativeTest() {

        //given
        BankDto bankDto = new BankDto(BigDecimal.valueOf(100));
        User loggedUser = new User("test@test.fr", "pass", BigDecimal.valueOf(1000));

        //when
        when(userService.getPrincipal()).thenReturn(loggedUser);

        //then
        assertThrows(NoBankAccountException.class, () -> transactionService.deposit(bankDto));
    }


    @Test
    @DisplayName("Should subtract money to wallet")
    void withdrawPositiveTest() {

        //given
        BankDto bankDto = new BankDto(BigDecimal.valueOf(100));
        User loggedUser = new User("test@test.fr", "pass", BigDecimal.valueOf(1000), "name", "number");
        BigDecimal expectedBalance = BigDecimal.valueOf(900).setScale(2, RoundingMode.HALF_DOWN);

        //when
        when(userService.getPrincipal()).thenReturn(loggedUser);
        transactionService.withdraw(bankDto);

        //then
        verify(userRepository, times(1)).save(loggedUser);
        assertEquals(expectedBalance, loggedUser.getBalance());
    }


    @Test
    @DisplayName("Should throw exception when no bank account set")
    void withdrawNegativeTest() {

        //given
        BankDto bankDto = new BankDto(BigDecimal.valueOf(100));
        User loggedUser = new User("test@test.fr", "pass", BigDecimal.valueOf(1000));

        //when
        when(userService.getPrincipal()).thenReturn(loggedUser);

        //then
        assertThrows(NoBankAccountException.class, () -> transactionService.withdraw(bankDto));
    }


//    @Test
//    @DisplayName("Should merge two lists")
//    void getMergeListPositiveTest() {
//
//        //given
//        User loggedUser = new User("test@test.fr", "pass");
//        loggedUser.setCreditList(new ArrayList<>());
//        loggedUser.setDebitList(new ArrayList<>());
//
//        //when
//        when(userService.getPrincipal()).thenReturn(loggedUser);
//        transactionService.getMergeList();
//
//        //then
//        assertTrue(transactionService.getMergeList().isEmpty());
//    }


    @Test
    @DisplayName("Should return true if loggedUser is beneficiary of the transaction")
    void isLoggedUserBeneficiaryPositiveTest() {

        //given
        User loggedUser = new User(1, "test@test.fr", "pass");
        User contactUser = new User(2, "test2@test.fr", "pass");
        Transaction transaction = new Transaction(contactUser, loggedUser);


        //when
        when(userService.getPrincipal()).thenReturn(loggedUser);
        boolean result = transactionService.isLoggedUserBeneficiary(transaction);

        //then
        assertTrue(result);
    }



//    @Test
//    @DisplayName("Should return a custom list from the merge list with same size")
//    void getCustomListPositiveTest() {
//
//        //given
//        User loggedUser = new User(1, "test@test.fr", "pass");
//        User contactUser = new User(2, "test2@test.fr", "pass");
//
//        Transaction transaction1 = new Transaction(contactUser, loggedUser);
//        Transaction transaction2 = new Transaction(contactUser, loggedUser);
//        Transaction transaction3 = new Transaction(contactUser, loggedUser);
//
//        List<Transaction> transactionList = new ArrayList<>();
//        transactionList.add(transaction1);
//        transactionList.add(transaction2);
//        transactionList.add(transaction3);
//
//        //when
//        doReturn(transactionList).when(transactionService).getMergeList();
//        doReturn(true).when(transactionService).isLoggedUserBeneficiary(any(Transaction.class));
//        List<TransactionDto> resultList = transactionService.getCustomList();
//
//        //then
//        assertEquals(3, resultList.size());
//    }


//    @Test
//    @DisplayName("Should return an ordered list")
//    void getSortedListPositiveTest() {
//
//        //given
//        TransactionDto transaction1 = new TransactionDto("test@test.fr", BigDecimal.valueOf(100), "desc");
//        LocalDateTime date1 = LocalDate.of(2000, 1, 1).atStartOfDay();
//        transaction1.setDate(Date.from(date1.atZone(ZoneId.of("UTC")).toInstant()));
//
//        TransactionDto transaction2 = new TransactionDto("test@test.fr", BigDecimal.valueOf(100), "desc");
//        LocalDateTime date2 = LocalDate.of(1800, 1, 1).atStartOfDay();
//        transaction2.setDate(Date.from(date2.atZone(ZoneId.of("UTC")).toInstant()));
//
//        TransactionDto transaction3 = new TransactionDto("test@test.fr", BigDecimal.valueOf(100), "desc");
//        LocalDateTime date3 = LocalDate.of(1900, 1, 1).atStartOfDay();
//        transaction3.setDate(Date.from(date3.atZone(ZoneId.of("UTC")).toInstant()));
//
//        List<TransactionDto> transactionList = new ArrayList<>();
//        transactionList.add(transaction1);
//        transactionList.add(transaction2);
//        transactionList.add(transaction3);
//
//        List<TransactionDto> expectedSortedList = new ArrayList<>();
//        expectedSortedList.add(transaction1);
//        expectedSortedList.add(transaction3);
//        expectedSortedList.add(transaction2);
//
//        //when
//        List<TransactionDto> sortedList = transactionService.getSortedList(transactionList);
//
//        //then
//        assertEquals(expectedSortedList, sortedList);
//    }


}