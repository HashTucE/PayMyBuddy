package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.dto.TransactionDto;
import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.service.TransactionService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


@ContextConfiguration(classes = {TransactionController.class})
@ExtendWith(SpringExtension.class)
class TransactionControllerTest {

    @Autowired
    private TransactionController transactionController;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private UserService userService;



    @Test
    @DisplayName("Check receiveFromBank controller")
    void receiveFromBank() throws Exception {

        doNothing().when(transactionService).deposit(any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transfer/fromBank");
        MockMvcBuilders.standaloneSetup(transactionController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("bankDto", "transactionDto"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/transfer"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/transfer"));
    }

    @Test
    @DisplayName("Check sendToBank controller")
    void sendToBankTest() throws Exception {

        doNothing().when(transactionService).withdraw(any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transfer/toBank");
        MockMvcBuilders.standaloneSetup(transactionController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("bankDto", "transactionDto"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/transfer"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/transfer"));
    }

    @Test
    @DisplayName("Check sendToBuddy controller")
    void sendToBuddyTest() throws Exception {

        doNothing().when(transactionService).makeTransaction(any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/transfer/toBuddy");

        MockMvcBuilders.standaloneSetup(transactionController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("bankDto", "transaction", "transactionDto"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/transfer"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/transfer"));
    }

//    @Test
//    void testViewTransfer() throws Exception {
//
//        User user = new User("test@test.fr", "pass", BigDecimal.ZERO);
//        user.setCreditList(new ArrayList<>());
//        user.setDebitList(new ArrayList<>());
//
//        when(userService.getPrincipal()).thenReturn(user);
//        when(transactionService.getCustomList()).thenReturn(new ArrayList<>());
//        when(transactionService.getSortedList((List<TransactionDto>) any())).thenReturn(new ArrayList<>());
//
//        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders
//                .formLogin();
//
//        MockMvcBuilders.standaloneSetup(transactionController)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//    }
}

