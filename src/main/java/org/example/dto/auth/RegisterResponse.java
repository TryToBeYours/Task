package org.example.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterResponse {

    @NotBlank
    private String Username;

    @NotBlank
    private String Password;

    @NotBlank
    @Email
    private String Email;


    public String getUsername(String Username) {
        return Username;
    }
    public void setUsername(String username) {
        this.Username = username;
    }

    public String getPassword(String Password) {
        return Password;
    }
    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getEmail(String Email) {
        return Email;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }



}
