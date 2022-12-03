package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.dto.BankDto;
import com.openclassrooms.paymybuddy.dto.TransactionDto;
import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.service.TransactionService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
class TransactionControllerTest {


    @InjectMocks
    private TransactionController transactionController;

    @Mock
    TransactionService transactionService;

    @Mock
    UserService userService;


    @Test
    void bankDtoTest() {assertThat(transactionController.bankDto()).isInstanceOf(BankDto.class);}

    @Test
    void transactionDtoTest() {assertThat(transactionController.transactionDto()).isInstanceOf(TransactionDto.class);}


    @Test
    void viewTransferTest() {

        //given//when
        ModelAndView mav = transactionController.viewTransfer();

        //then
        assertEquals("redirect:/transfer/page/1", mav.getViewName());
    }


    @Test
    void getOnePagePositiveTest() {

        //given
        User user = new User("user@test.fr", "pass", BigDecimal.valueOf(100));
        Set<User> contacts = new HashSet<>();
        user.setContacts(contacts);
        TransactionDto transaction1 = new TransactionDto("a@a.a", BigDecimal.valueOf(100), "desc");
        TransactionDto transaction2 = new TransactionDto("a@a.a", BigDecimal.valueOf(100), "desc");
        List<TransactionDto> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);
        Page<TransactionDto> page = new PageImpl<>(transactions);

        //when
        when(userService.getPrincipal()).thenReturn(user);
        when(transactionService.getPageTransactionDto(anyInt())).thenReturn(page);
        ModelAndView mav = transactionController.getOnePage(1);

        //then
        assertEquals("/transfer", mav.getViewName());
        assertEquals(user.getContacts(), mav.getModel().get("contacts"));
        assertEquals(user.getBalance(), mav.getModel().get("balance"));
        assertEquals(transactions, mav.getModel().get("transactions"));
        assertEquals((long) 2, mav.getModel().get("totalItems"));
        assertEquals(1, mav.getModel().get("totalPages"));
        assertEquals(1, mav.getModel().get("currentPage"));
    }



    @Test
    void receiveFromBankTest() {

        //given
        BankDto bankDto = new BankDto(BigDecimal.valueOf(100));

        //when
        doNothing().when(transactionService).deposit(bankDto);
        ModelAndView mav = transactionController.receiveFromBank(bankDto);

        //then
        verify(transactionService, times(1)).deposit(any(BankDto.class));
        assertEquals("redirect:/transfer", mav.getViewName());
        assertTrue(mav.getModel().containsKey("bankDto"));
        assertEquals(1, mav.getModel().size());
    }


    @Test
    void sendToBankTest() {

        //given
        BankDto bankDto = new BankDto(BigDecimal.valueOf(100));

        //when
        doNothing().when(transactionService).withdraw(bankDto);
        ModelAndView mav = transactionController.sendToBank(bankDto);

        //then
        verify(transactionService, times(1)).withdraw(any(BankDto.class));
        assertEquals("redirect:/transfer", mav.getViewName());
        assertTrue(mav.getModel().containsKey("bankDto"));
        assertEquals(1, mav.getModel().size());
    }



    @Test
    void sendToBuddyTest() {

        //given
        TransactionDto transactionDto = new TransactionDto("a@a.a", BigDecimal.valueOf(100), "desc");

        //when
        doNothing().when(transactionService).makeTransaction(transactionDto);
        ModelAndView mav = transactionController.sendToBuddy(transactionDto);

        //then
        verify(transactionService, times(1)).makeTransaction(any(TransactionDto.class));
        assertEquals("redirect:/transfer", mav.getViewName());
        assertTrue(mav.getModel().containsKey("transactionDto"));
        assertEquals(1, mav.getModel().size());
    }





}















