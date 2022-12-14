package com.openclassrooms.paymybuddy.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class ContactDto {


    @NotNull
    @Email
    private String email;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public ContactDto() {
    }


    public ContactDto(String email) {
        this.email = email;
    }
}
