package com.CodeLyser.CoderPad_UserService.Model;

public class LoginResponse {

    private String jwtToken;
    private String role;
    private Long id;

    public LoginResponse(String jwtToken, String role, Long id) {
        this.jwtToken = jwtToken;
        this.role = role;
        this.id = id;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getRole() {
        return role;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public void setRole(String role) {
        this.role = role;
    }
}
