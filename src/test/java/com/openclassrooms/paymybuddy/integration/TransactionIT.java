package com.openclassrooms.paymybuddy.integration;

import com.openclassrooms.paymybuddy.dto.BankDto;
import com.openclassrooms.paymybuddy.dto.TransactionDto;
import com.openclassrooms.paymybuddy.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TransactionIT {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;



    @Test
    @WithMockUser(username = "test@paymybuddy.com")
    void viewTransferIT() throws Exception {
        mockMvc.perform(get("/transfer"))
                .andExpect(status().isFound())
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/transfer/page/1"))
                .andExpect(view().name("redirect:/transfer/page/1"))
                .andReturn();
    }



    @Test
    @WithMockUser(username = "test@paymybuddy.com")
    void receiveFromBankIT() throws Exception {

        mockMvc.perform(post("/transfer/fromBank")
                        .sessionAttr("bankDTO", new BankDto())
                        .param("amount", String.valueOf(BigDecimal.valueOf(100))))
                .andExpect(status().isFound())
                .andExpect(model().size(1))
                .andExpect(authenticated())
                .andExpect(model().attributeExists("bankDto"))
                .andExpect(redirectedUrl("/transfer"))
                .andExpect(view().name("redirect:/transfer"))
                .andReturn();
    }


    @Test
    @WithMockUser(username = "test@paymybuddy.com")
    void sendToBankIT() throws Exception {

        mockMvc.perform(post("/transfer/toBank")
                        .sessionAttr("bankDTO", new BankDto())
                        .param("amount", String.valueOf(BigDecimal.valueOf(100))))
                .andExpect(status().isFound())
                .andExpect(model().size(1))
                .andExpect(authenticated())
                .andExpect(model().attributeExists("bankDto"))
                .andExpect(redirectedUrl("/transfer"))
                .andExpect(view().name("redirect:/transfer"))
                .andReturn();
    }


    @Test
    @WithMockUser(username = "test@paymybuddy.com")
    void sendToBuddyIT() throws Exception {

        mockMvc.perform(post("/transfer/toBuddy")
                        .sessionAttr("transactionDTO", new TransactionDto())
                        .param("email", "test2@paymybuddy.com")
                        .param("amount", String.valueOf(BigDecimal.valueOf(100)))
                        .param("description", "desc"))
                .andExpect(status().isFound())
                .andExpect(model().size(1))
                .andExpect(authenticated())
                .andExpect(model().attributeExists("transactionDto"))
                .andExpect(redirectedUrl("/transfer"))
                .andExpect(view().name("redirect:/transfer"))
                .andReturn();
    }
}
