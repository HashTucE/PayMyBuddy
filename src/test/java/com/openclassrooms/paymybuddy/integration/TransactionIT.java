package com.openclassrooms.paymybuddy.integration;

import com.openclassrooms.paymybuddy.controller.TransactionController;
import com.openclassrooms.paymybuddy.service.TransactionService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionIT {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;



    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new TransactionController()).build();
    }





    @Test
    void viewTransferIT() throws Exception {
        this.mockMvc.perform(get("/transfer").with(user("test@paymybuddy.com")))
                .andExpect(status().isFound())
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/transfer/page/1"))
                .andExpect(view().name("redirect:/transfer/page/1"))
                .andReturn();
    }




//    @Test
//    void getOnePageIT() throws Exception {
//        this.mockMvc.perform(get("/transfer/page/{pageNumber}").with(user("test@paymybuddy.com")))
//                .andExpect(status().isOk())
//                .andExpect(authenticated())
//                .andExpect(model().attributeExists("contacts"))
//                .andExpect(model().attributeExists("balance"))
//                .andExpect(model().attributeExists("currentPage"))
//                .andExpect(model().attributeExists("totalItems"))
//                .andExpect(model().attributeExists("totalPages"))
//                .andExpect(model().attributeExists("transactions"))
//                .andExpect(view().name("transfer"))
//                .andReturn();
//    }
//
//
//    @Test
//    void receiveFromBankIT() throws Exception {
//
//        doNothing().when(transactionService).deposit(any());
//
//        this.mockMvc.perform(post("/transfer/fromBank").with(user("test@paymybuddy.com")))
//                .andExpect(status().isOk())
//                .andExpect(model().size(2))
//                .andExpect(authenticated())
//                .andExpect(model().attributeExists("bankDto", "transactionDto"))
//                .andExpect(redirectedUrl("/transfer"))
//                .andExpect(view().name("redirect:/transfer"))
//                .andReturn();
//    }
//
//
//    @Test
//    void sendToBankIT() throws Exception {
//
//        doNothing().when(transactionService).withdraw(any());
//
//        this.mockMvc.perform(post("/transfer/toBank").with(user("test@paymybuddy.com")))
//                .andExpect(status().isOk())
//                .andExpect(model().size(2))
//                .andExpect(authenticated())
//                .andExpect(model().attributeExists("bankDto", "transactionDto"))
//                .andExpect(redirectedUrl("/transfer"))
//                .andExpect(view().name("redirect:/transfer"))
//                .andReturn();
//    }
////
////
//    @Test
//    void sendToBuddyIT() throws Exception {
//
//        doNothing().when(transactionService).makeTransaction(any());
//
//        this.mockMvc.perform(post("/transfer/toBuddy").with(user("test@paymybuddy.com")))
//                .andExpect(status().isFound())
//                .andExpect(model().size(3))
//                .andExpect(authenticated())
//                .andExpect(model().attributeExists("transaction", "transactionDto"))
//                .andExpect(redirectedUrl("/transfer"))
//                .andExpect(view().name("redirect:/transfer"))
//                .andReturn();
//    }
}
