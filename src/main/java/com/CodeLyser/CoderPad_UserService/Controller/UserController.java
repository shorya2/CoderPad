package com.CodeLyser.CoderPad_UserService.Controller;

import com.CodeLyser.CoderPad_UserService.Model.User;
import com.CodeLyser.CoderPad_UserService.Model.userRegisterModel;
import com.CodeLyser.CoderPad_UserService.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody userRegisterModel user){
        return userService.registerUser(user.getFirstName(),user.getLastName(),user.getEmail(),user.getPassword(),user.getRoleName());
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password){
        return userService.loginUser(email,password);
    }

    @PutMapping("/update/{userID}")
    public String updateUser(@PathVariable long userID,@RequestParam String email,
                             @RequestParam String firstName, @RequestParam String lastName){
        return userService.updateUser(userID,email,firstName,lastName);
    }

    @PutMapping("/updatePassword/{userID}")
    public String updateUserPassword(@PathVariable long userID,@RequestParam String email,
                                     @RequestParam String currentPassword, @RequestParam String newPassword){
        return userService.updateUserPassword(userID,email,currentPassword,newPassword);
    }

    @GetMapping("/admin/getAllNotApproved")
    public List<User> getUsersNotApproved(){
        return userService.getUsersNotApproved();
    }

    @PutMapping("/admin/approveUser")
    public String approveUser(String userEmail){
        return userService.approveUser(userEmail);
    }

    @GetMapping("/admin/getAllUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
}
