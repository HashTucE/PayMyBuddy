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
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
        User loggedUser = new User("test@test.fr", "pass", BigDecimal.valueOf(1000), " ", " ");

        //when
        when(userService.getPrincipal()).thenReturn(loggedUser);

        //then
        assertThrows(NoBankAccountException.class, () -> transactionService.deposit(bankDto));
    }


    @Test
    @DisplayName("Should throw exception when only bank name is set")
    void depositNegativeTest2() {

        //given
        BankDto bankDto = new BankDto(BigDecimal.valueOf(100));
        User loggedUser = new User("test@test.fr", "pass", BigDecimal.valueOf(1000), "name", " ");

        //when
        when(userService.getPrincipal()).thenReturn(loggedUser);

        //then
        assertThrows(NoBankAccountException.class, () -> transactionService.deposit(bankDto));
    }


    @Test
    @DisplayName("Should throw exception when only account number is set")
    void depositNegativeTest3() {

        //given
        BankDto bankDto = new BankDto(BigDecimal.valueOf(100));
        User loggedUser = new User("test@test.fr", "pass", BigDecimal.valueOf(1000), " ", "FR12");

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
        User loggedUser = new User("test@test.fr", "pass", BigDecimal.valueOf(1000), " ", " ");

        //when
        when(userService.getPrincipal()).thenReturn(loggedUser);

        //then
        assertThrows(NoBankAccountException.class, () -> transactionService.withdraw(bankDto));
    }


    @Test
    @DisplayName("Should throw exception when only bank name is set")
    void withdrawNegativeTest2() {

        //given
        BankDto bankDto = new BankDto(BigDecimal.valueOf(100));
        User loggedUser = new User("test@test.fr", "pass", BigDecimal.valueOf(1000), "name", " ");

        //when
        when(userService.getPrincipal()).thenReturn(loggedUser);

        //then
        assertThrows(NoBankAccountException.class, () -> transactionService.withdraw(bankDto));
    }


    @Test
    @DisplayName("Should throw exception when only account number is set")
    void withdrawNegativeTest3() {

        //given
        BankDto bankDto = new BankDto(BigDecimal.valueOf(100));
        User loggedUser = new User("test@test.fr", "pass", BigDecimal.valueOf(1000), " ", "FR12");

        //when
        when(userService.getPrincipal()).thenReturn(loggedUser);

        //then
        assertThrows(NoBankAccountException.class, () -> transactionService.withdraw(bankDto));
    }


    @Test
    @DisplayName("Should throw exception when withdraw is superior than balance")
    void withdrawPositiveTest4() {

        //given
        BankDto bankDto = new BankDto(BigDecimal.valueOf(1000));
        User loggedUser = new User("test@test.fr", "pass", BigDecimal.valueOf(100), "name", "number");

        //when
        when(userService.getPrincipal()).thenReturn(loggedUser);

        //then
        assertThrows(InsufficientFundException.class, () -> transactionService.withdraw(bankDto));
    }


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


    @Test
    @DisplayName("should return an empty page when there is no transaction")
    void getPageTransactionDtoNegativeTest() {

        //given
        User loggedUser = new User("test@test.fr", "pass", BigDecimal.valueOf(1000));

        //when
        when(userService.getPrincipal()).thenReturn(loggedUser);
        when(transactionRepository.findAllTransactionsById(anyInt(), any())).thenReturn(new PageImpl<>(new ArrayList<>()));

        //then
        assertTrue(transactionService.getPageTransactionDto(1).toList().isEmpty());
        verify(transactionRepository).findAllTransactionsById(anyInt(), any());
        verify(userService).getPrincipal();
    }


    @Test
    @DisplayName("should return a dto from entity as beneficiary logged user")
    void transactionEntityToDtoTest() {

        //given
        User sender = new User("sender@test.fr", "pass", BigDecimal.valueOf(100));
        sender.setContacts(new HashSet<>());
        sender.setCreditList(new ArrayList<>());
        sender.setDebitList(new ArrayList<>());

        User beneficiary = new User("beneficiary@test.fr", "pass", BigDecimal.valueOf(50));
        beneficiary.setContacts(new HashSet<>());
        beneficiary.setCreditList(new ArrayList<>());
        beneficiary.setDebitList(new ArrayList<>());

        Transaction transaction = new Transaction(sender, beneficiary, BigDecimal.valueOf(50), "desc");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        transaction.setDate(fromResult);

        ArrayList<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);
        PageImpl<Transaction> pageImpl = new PageImpl<>(transactionList);
        when(transactionRepository.findAllTransactionsById(anyInt(), any())).thenReturn(pageImpl);

        User loggedUser = new User("beneficiary@test.fr", "pass", BigDecimal.valueOf(50));
        loggedUser.setContacts(new HashSet<>());
        loggedUser.setCreditList(new ArrayList<>());
        loggedUser.setDebitList(new ArrayList<>());

        BigDecimal valueOfResult = BigDecimal.valueOf(50);


        //when
        when(userService.getPrincipal()).thenReturn(loggedUser);
        List<TransactionDto> toListResult = transactionService.getPageTransactionDto(1).toList();

        //then
        assertEquals(1, toListResult.size());
        TransactionDto getResult = toListResult.get(0);

        BigDecimal amount = getResult.getAmount();
        assertEquals(valueOfResult, amount);
        assertEquals("50", amount.toString());

        assertEquals("+", getResult.getSign());
        assertSame(fromResult, getResult.getDate());
        assertEquals("sender@test.fr", getResult.getEmail());
        assertEquals("desc", getResult.getDescription());

        verify(transactionRepository, times(1)).findAllTransactionsById(anyInt(), any());
        verify(userService, atLeast(1)).getPrincipal();
    }


    @Test
    @DisplayName("should return a dto from entity as sender logged user")
    void transactionEntityToDtoTest2() {

        //given
        User sender = new User("sender@test.fr", "pass", BigDecimal.valueOf(100));
        sender.setContacts(new HashSet<>());
        sender.setCreditList(new ArrayList<>());
        sender.setDebitList(new ArrayList<>());

        User beneficiary = new User("beneficiary@test.fr", "pass", BigDecimal.valueOf(50));
        beneficiary.setContacts(new HashSet<>());
        beneficiary.setCreditList(new ArrayList<>());
        beneficiary.setDebitList(new ArrayList<>());

        Transaction transaction = new Transaction(sender, beneficiary, BigDecimal.valueOf(50), "desc");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        transaction.setDate(fromResult);

        ArrayList<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);
        PageImpl<Transaction> pageImpl = new PageImpl<>(transactionList);
        when(transactionRepository.findAllTransactionsById(anyInt(), any())).thenReturn(pageImpl);

        User loggedUser = new User("sender@test.fr", "pass", BigDecimal.valueOf(100));
        loggedUser.setContacts(new HashSet<>());
        loggedUser.setCreditList(new ArrayList<>());
        loggedUser.setDebitList(new ArrayList<>());

        BigDecimal valueOfResult = BigDecimal.valueOf(50);


        //when
        when(userService.getPrincipal()).thenReturn(loggedUser);
        List<TransactionDto> toListResult = transactionService.getPageTransactionDto(1).toList();

        //then
        assertEquals(1, toListResult.size());
        TransactionDto getResult = toListResult.get(0);

        BigDecimal amount = getResult.getAmount();
        assertEquals(valueOfResult, amount);
        assertEquals("50", amount.toString());

        assertEquals("-", getResult.getSign());
        assertSame(fromResult, getResult.getDate());
        assertEquals("beneficiary@test.fr", getResult.getEmail());
        assertEquals("desc", getResult.getDescription());

        verify(transactionRepository, times(1)).findAllTransactionsById(anyInt(), any());
        verify(userService, atLeast(1)).getPrincipal();
    }

}





