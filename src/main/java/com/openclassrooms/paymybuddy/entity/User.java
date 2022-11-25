package com.openclassrooms.paymybuddy.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.PERSIST;

@Entity
@Table(name = "users")
public class User implements UserDetails {



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade={PERSIST, DETACH})
    @JoinTable(name = "user_contacts",
            joinColumns = {@JoinColumn(name="userId")},
            inverseJoinColumns = {@JoinColumn(name="contactId")}
    )
    private Set<User> contacts = new HashSet<>();


    @Column(name = "balance", nullable = false)
    private BigDecimal balance;


    @Column(name = "bank_name")
    private String bankName;


    @Column(name = "account_number")
    private String accountNumber;


    @OneToMany(mappedBy = "sender", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private List<Transaction> debitList = new ArrayList<>();


    @OneToMany(mappedBy = "beneficiary", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private List<Transaction> creditList = new ArrayList<>();




    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(int userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, BigDecimal balance) {
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    public User(String email, String password, String bankName, String accountNumber) {
        this.email = email;
        this.password = password;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
    }

    public User(String email, String password, BigDecimal balance, String bankName, String accountNumber) {
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
    }

    public User(String email, List<Transaction> debitList, List<Transaction> creditList) {
        this.email = email;
        this.debitList = debitList;
        this.creditList = creditList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public Set<User> getContacts() {
        return contacts;
    }

    public void setContacts(Set<User> contacts) {
        this.contacts = contacts;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    public List<Transaction> getDebitList() {
        return debitList;
    }

    public void setDebitList(List<Transaction> debitList) {
        this.debitList = debitList;
    }

    public List<Transaction> getCreditList() {
        return creditList;
    }

    public void setCreditList(List<Transaction> creditList) {
        this.creditList = creditList;
    }






    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }




    @Override
    public String toString() {
        return "User{" + "id=" + userId + ", email='" + email + ", sold=" + balance + '}';
    }
}
