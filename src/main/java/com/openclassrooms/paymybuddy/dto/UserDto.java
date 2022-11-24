package com.openclassrooms.paymybuddy.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserDto implements Serializable {

    @NotNull
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    @NotNull
    @NotEmpty(message = "Password should not be empty")
    private String password;







    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public UserDto() {
    }

    public UserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
