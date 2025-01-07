package com.CodeLyser.CoderPad_UserService.Controller;

import com.CodeLyser.CoderPad_UserService.Model.LoginResponse;
import com.CodeLyser.CoderPad_UserService.Model.User;
import com.CodeLyser.CoderPad_UserService.Model.UserDTO;
import com.CodeLyser.CoderPad_UserService.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody UserDTO user){
        return userService.registerUser(user.getUserName(),user.getEmail(),user.getPassword(),user.getRoleName());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestParam String email, @RequestParam String password){
        try {
            // Get JWT Token and Role
            LoginResponse loginResponse = userService.loginUser(email,password);
            return ResponseEntity.ok(loginResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestParam Long userId){
        User user = userService.getUserProfile(userId);
        if(user != null){
            user.setPassword(null);  // Do not return the password field
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/update/{userID}")
    //@PreAuthorize("hasRole('candidate')")
    public ResponseEntity<Map<String, String>> updateUser(@PathVariable long userID, @RequestBody UserDTO userDTO) {

        String userName = userDTO.getUserName();
        String email = userDTO.getEmail();
        System.out.println("email: " + email);
        // Check if the required details are provided
        if (userName == null || email == null || userName.isEmpty() || email.isEmpty()) {
            return ResponseEntity.status(400).body(Map.of("message", "Username and email cannot be empty"));
        }

        // Perform the update using these details
        String response = userService.updateUser(userID, userName, email);

        if ("Success".equals(response)) {
            return ResponseEntity.status(200).body(Map.of("message", "Profile updated successfully"));
        } else {
            return ResponseEntity.status(404).body(Map.of("message", "User not found or update failed"));
        }
    }


    // Method to update user password (without DTO)
    @PatchMapping("/updatePassword/{userID}")
    public ResponseEntity<String> updateUserPassword(@PathVariable long userID,
                                                     @RequestBody UserDTO userDTO) {
        // Extract current and new password from the request body
        String newPassword = userDTO.getPassword();


        // Perform the password update
        String response = userService.updateUserPassword(userID,  newPassword);

        if ("Success".equals(response)) {
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.status(404).body("User not found or password update failed");
        }
    }


    @GetMapping("/admin/getAllNotApproved")
    public List<User> getUsersNotApproved(){
        return userService.getUsersNotApproved();
    }

    @PutMapping("/admin/approveUser")
    public String approveUser(@RequestParam String userEmail)
    {
        return userService.approveUser(userEmail);
    }

    @GetMapping("/admin/getAllUsers")
    public List<User> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return users;
    }

    @DeleteMapping("/admin/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam Long id) {
        try {
            boolean isDeleted = userService.deleteUserById(id); // Call your service to delete the user by email
            if (isDeleted) {
                return ResponseEntity.ok("User deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
        }
    }



}
