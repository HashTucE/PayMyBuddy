package com.openclassrooms.paymybuddy.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class TransactionDto {




    @NotNull
    private String email;

    @NotNull
    private Date date;

    @NotNull
    @NotEmpty(message = "Amount should not be empty")
    private BigDecimal amount;

    @NotNull
    @NotEmpty(message = "Description should not be empty")
    private String description;

    @NotNull
    private String sign;


    public String getEmail() {
        return email;
    }

    public void setEmail(String beneficiaryEmail) {
        this.email = beneficiaryEmail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }


    @Override
    public String toString() {
        return this.date + " + " + this.description + " + " + this.amount + "dto";
    }
}
