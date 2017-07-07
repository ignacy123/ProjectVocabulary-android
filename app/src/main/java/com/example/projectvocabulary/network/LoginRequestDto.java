package com.example.projectvocabulary.network;

/**
 * Created by ignacy on 14.06.17.
 */

public class LoginRequestDto {
    private String email;
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

    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
