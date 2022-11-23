package com.openclassrooms.paymybuddy.service;



import com.openclassrooms.paymybuddy.entity.User;

import com.openclassrooms.paymybuddy.repository.UserRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Objects;

@Service
@Transactional
public class UserService {


    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final Logger log = LogManager.getLogger(UserService.class);


    @Autowired
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }






    /**
     * Find a user from his email
     * @param email of user
     * @return user
     */
    public User findByEmail(String email) {
        User user;
        try {
            user = userRepository.findByEmail(email);
            log.debug(email + " founded");

        } catch (Exception UserNotExistingException) {
            log.error(email + " not existing");
            throw UserNotExistingException;
        }
        return user;
    }










}
