package com.openclassrooms.paymybuddy.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int transactionId;

    @Column(name = "date")
    private Date date;

    @ManyToOne()
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    @ManyToOne()
    @JoinColumn(name = "beneficiary_id", referencedColumnName = "id")
    private User beneficiary;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "description")
    private String description;


    public Transaction() {
    }

    public Transaction(User sender, User beneficiary) {
        this.sender = sender;
        this.beneficiary = beneficiary;
    }

    public Transaction(User sender, User beneficiary, BigDecimal amount, String description) {
        this.sender = sender;
        this.beneficiary = beneficiary;
        this.amount = amount;
        this.description = description;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(User beneficiary) {
        this.beneficiary = beneficiary;
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

    @Override
    public String toString() {
        return this.description + " + " + this.amount;
    }
}
