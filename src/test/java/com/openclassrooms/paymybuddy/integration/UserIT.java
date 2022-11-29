package com.openclassrooms.paymybuddy.integration;

import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import com.openclassrooms.paymybuddy.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserIT {


    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;



    private User userIT;

    @AfterEach
    void deleteUser() {
        int id = userIT.getUserId();
        userRepository.deleteById(id);
    }

    @Test
    @DisplayName("Should create and return a user from userDto")
    public void createUser() {

        //given
        UserDto userDto = new UserDto("userIT@test.fr", "pass");

        //when
        userIT = userService.createUser(userDto);

        //then
        assertThat(userIT).isNotNull();
        assertEquals("userIT@test.fr", userIT.getEmail());
    }


}
