package com.openclassrooms.paymybuddy.service;


import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.entity.Transaction;
import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.exceptions.InvalidEmailException;
import com.openclassrooms.paymybuddy.exceptions.UserAlreadyExistException;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private TransactionRepository transactionRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final Logger log = LogManager.getLogger(UserService.class);


    @Autowired
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }




    /**
     * Save a user
     * @param user user
     */
    public void save(User user) {

        userRepository.save(user);
        log.debug(user.getEmail() + " saved into DB");
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



    /**
     * create a user from his DTO
     *
     * @param userDto userDTO
     * @return user
     */
    public User createUser(UserDto userDto) {

        String email = userDto.getEmail();
        if (!isValidEmailAddress(email)) {
            log.error(email + " invalid");
            throw new InvalidEmailException(email + " invalid");
        }

        User userDB = userRepository.findByEmail(email);
        if (!Objects.isNull(userDB)) {
            log.error(email + " is not available");
            throw new UserAlreadyExistException(email + " is not available");
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setBalance(BigDecimal.ZERO);
        log.info(userDto.getEmail() + " created");
        userRepository.save(user);
        return user;
    }



    /**
     * Check if an email address is valid
     * @param email address
     * @return boolean
     */
    public boolean isValidEmailAddress(String email) {

        EmailValidator validator = EmailValidator.getInstance();
        log.info("Checking if " + email + " is valid");
        return validator.isValid(email);
    }



    /**
     * Determines the user connected
     * @return the logged user
     */
    public User getPrincipal() {

        //SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("toto", "tutu"));
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User principalUser = findByEmail(username);


        log.info(principalUser.getEmail() + " is the user connected");
        return principalUser;
    }








}
