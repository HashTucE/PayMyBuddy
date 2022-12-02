package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.dto.BankDto;
import com.openclassrooms.paymybuddy.dto.TransactionDto;
import com.openclassrooms.paymybuddy.entity.Transaction;
import com.openclassrooms.paymybuddy.entity.User;
import com.openclassrooms.paymybuddy.exceptions.InsufficientFundException;
import com.openclassrooms.paymybuddy.exceptions.NoBankAccountException;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Service
public class TransactionService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;


    private static final Logger log = LogManager.getLogger(TransactionService.class);



    /**
     * Calculate the amount of a transaction, 5% commission inclusive
     * @param amount amount for beneficiary
     * @return amount commission inclusive
     */
    public BigDecimal getFinalAmount(BigDecimal amount) {

        BigDecimal commission = new BigDecimal("1.005");
        log.info("Return the final amount from " + amount);
        return (amount.multiply(commission)).setScale(2, RoundingMode.HALF_DOWN);
    }




    /**
     * Send money from sender balance to beneficiary balance with description
     *
     * @param transactionDto transfer object
     */
    public void makeTransaction(TransactionDto transactionDto) {

        User loggedUser = userService.getPrincipal();
        User beneficiary = userService.findByEmail(transactionDto.getEmail());
        User payMyBuddy = userService.findByEmail("bill@paymybuddy.com");

        BigDecimal amount = transactionDto.getAmount();
        BigDecimal amountTaxIncl = getFinalAmount(amount);
        BigDecimal tax = amountTaxIncl.subtract(amount);

            if (loggedUser.getBalance().compareTo(amountTaxIncl) >= 0) {

                loggedUser.setBalance(loggedUser.getBalance().subtract(amountTaxIncl));
                userRepository.save(loggedUser);
                log.info("Amount of " + amountTaxIncl + " subtract from " + loggedUser.getEmail());

                beneficiary.setBalance(beneficiary.getBalance().add(amount));
                userRepository.save(beneficiary);
                log.info("Amount of " + amount + " added to " + beneficiary.getEmail());


                payMyBuddy.setBalance(payMyBuddy.getBalance().add(tax));
                userRepository.save(payMyBuddy);
                log.info("Amount of " + tax + " added to " + payMyBuddy.getEmail());


                Transaction transaction = new Transaction();
                Date date = new Date();
                date.setTime(System.currentTimeMillis());
                transaction.setDate(date);
                transaction.setSender(loggedUser);
                transaction.setBeneficiary(beneficiary);
                transaction.setDescription(transactionDto.getDescription());
                transaction.setAmount(amount);
                transactionRepository.save(transaction);
                log.info("Transaction from " + transaction.getDate() + " save in DB");

                loggedUser.getDebitList().add(transaction);
                userRepository.save(loggedUser);
                log.info("Transaction from " + transaction.getDate() + " save in debit's list of " + loggedUser.getEmail());

                beneficiary.getCreditList().add(transaction);
                userRepository.save(beneficiary);
                log.info("Transaction from " + transaction.getDate() + " save in credit's list of " + beneficiary.getEmail());

                payMyBuddy.getCreditList().add(transaction);
                userRepository.save(payMyBuddy);
                log.info("Transaction from " + transaction.getDate() + " save in debit's list of " + payMyBuddy.getEmail());

            }
            else {
                log.error(loggedUser.getEmail() + " does not have sufficient funds to carry out the transaction");
                throw new InsufficientFundException(loggedUser.getEmail() + " does not have sufficient funds to carry out the transaction");
            }
    }



    /**
     * Credit PayMyBuddy's balance from bank account
     * @param bankDto amount of deposit
     */
    public void deposit(BankDto bankDto) {

        BigDecimal roundedAmount = bankDto.getAmount().setScale(2, RoundingMode.HALF_DOWN);
        User loggedUser = userService.getPrincipal();

        if(loggedUser.getAccountNumber() == null) {
            log.error("Bank account not set for " + loggedUser.getEmail());
            throw new NoBankAccountException("Bank account not set for " + loggedUser.getEmail());
        } else {
            loggedUser.setBalance(loggedUser.getBalance().add(roundedAmount));
            userRepository.save(loggedUser);
            log.info(roundedAmount + " was added to the wallet of " + loggedUser);
        }
    }



    /**
     * Debit PayMyBuddy's balance to bank account
     * @param bankDto amount of withdraw
     */
    public void withdraw(BankDto bankDto) {

        BigDecimal roundedAmount = bankDto.getAmount().setScale(2, RoundingMode.HALF_DOWN);
        User loggedUser = userService.getPrincipal();


        if (loggedUser.getBalance().compareTo(bankDto.getAmount()) < 0) {
            log.error("Fund insufficient for " + loggedUser.getEmail());
            throw new InsufficientFundException("Fund insufficient for " + loggedUser.getEmail());
        } else if(loggedUser.getAccountNumber() != null) {
            loggedUser.setBalance(loggedUser.getBalance().subtract(roundedAmount));
            userRepository.save(loggedUser);
            log.info(roundedAmount + " was sent to the bank account of " + loggedUser);
        } else {
            log.error("Bank account not set for " + loggedUser.getEmail());
            throw new NoBankAccountException("Bank account not set for " + loggedUser.getEmail());
        }
    }




    /**
     * Determines if the logged user is the beneficiary of the transaction
     * @param transaction transaction
     * @return boolean
     */
    public boolean isLoggedUserBeneficiary(Transaction transaction) {

        User loggedUser = userService.getPrincipal();
        boolean beneficiary = loggedUser.getEmail().equals(transaction.getBeneficiary().getEmail());

        log.info(loggedUser.getEmail() + " is the beneficiary of the transaction ? " + beneficiary);
        return beneficiary;
    }




    /**
     * Sort by descending date order the whole transaction list of the logged user in a page
     * @return page of transactionDto
     */
    public Page<TransactionDto> getPageTransactionDto(int pageNumber) {

        User loggedUser = userService.getPrincipal();
        Pageable sortedByDate = PageRequest.of(pageNumber - 1, 5, Sort.by("date").descending());
        Page<Transaction> transactionPage = transactionRepository.findAllTransactionsById(loggedUser.getUserId(), sortedByDate);

        return transactionPage.map(this::transactionEntityToDto);
    }


    /**
     * Transform the transaction entity to dto
     * @param transaction transaction
     * @return TransactionDto
     */
    public TransactionDto transactionEntityToDto(Transaction transaction){
        TransactionDto dto =  new TransactionDto();
        if (isLoggedUserBeneficiary(transaction)) {
                dto.setEmail(transaction.getSender().getEmail());
            } else {
                dto.setEmail(transaction.getBeneficiary().getEmail());
            }
            dto.setAmount(transaction.getAmount());
            dto.setDescription(transaction.getDescription());
            dto.setDate(transaction.getDate());
            if (isLoggedUserBeneficiary(transaction)) {
                dto.setSign("+");
            } else {
                dto.setSign("-");
            }
        return dto;
    }




}
