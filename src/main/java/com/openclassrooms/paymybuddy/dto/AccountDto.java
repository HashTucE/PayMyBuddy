package com.openclassrooms.paymybuddy.dto;

import javax.validation.constraints.NotNull;

public class AccountDto {

    @NotNull
    private String bankName;

    @NotNull
    private String accountNumber;


    public String getBankName() {
        return bankName;
    }


    public String getAccountNumber() {
        return accountNumber;
    }


    public AccountDto() {
    }

    public AccountDto(String bankName, String accountNumber) {
        this.bankName = bankName;
        this.accountNumber = accountNumber;
    }
}
