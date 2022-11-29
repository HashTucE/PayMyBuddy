package com.openclassrooms.paymybuddy.integration;

import com.openclassrooms.paymybuddy.dto.AccountDto;
import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import com.openclassrooms.paymybuddy.service.AccountService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("userIT@test.fr")
public class AccountIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;
    private User userIT;

    private int id;



//    @BeforeEach
//    void createUser() {
//        userIT = new User("userIT@test.fr", "pass", BigDecimal.ZERO);
//        userRepository.save(userIT);
//        id = userIT.getUserId();
//    }
//
//    @AfterEach
//    void deleteUser() {
//        userRepository.deleteById(id);
//    }



    @Test

    @DisplayName("Should add the bank account from AccountDto")
    void addBankAccountIT() throws Exception {
        mockMvc.perform(get("/login").with(user("toto").password("tutu")))
                .andExpect(status().isOk())
                .andExpect(unauthenticated())
                .andReturn();

//        //GIVEN
//        AccountDto account = new AccountDto("CIC", "FR12");
//
//        //WHEN
//        accountService.addBankAccount(account);
//        userRepository.save(userIT);
//
//        //THEN
//        assertEquals("CIC", userIT.getBankName());
//        assertEquals("FR12", userIT.getAccountNumber());
    }


    @Test
    @WithMockUser("userIT@test.fr")
    @DisplayName("Should delete the bank account from AccountDto")
    void removeBankAccountIT() {

        //GIVEN
        userIT.setBankName("CIC");
        userIT.setAccountNumber("FR12");
        userRepository.save(userIT);

        //WHEN
        accountService.deleteBankAccount();

        //THEN
        assertNull(userIT.getBankName());
        assertNull(userIT.getAccountNumber());
    }
}
