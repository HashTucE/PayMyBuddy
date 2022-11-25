package com.openclassrooms.paymybuddy.dto;

import java.math.BigDecimal;

public class BankDto {


    private BigDecimal amount;


    public BigDecimal getAmount() {
        return amount;
    }

    public BankDto(BigDecimal amount) {
        this.amount = amount;
    }

    public BankDto() {
    }
}
