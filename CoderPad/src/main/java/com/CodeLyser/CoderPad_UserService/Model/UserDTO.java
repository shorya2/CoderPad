package com.CodeLyser.CoderPad_UserService.Model;

public class UserDTO {
    private String userName;
    private String email;
    private String password;
    private String roleName;

    public String getUserName() {return userName;}

    public void setUserName(String userName) {this.userName = userName;}

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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
