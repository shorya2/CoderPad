package com.CodeLyser.CoderPad_UserService.Services;

import com.CodeLyser.CoderPad_UserService.Model.LoginResponse;
import com.CodeLyser.CoderPad_UserService.Model.User;
import com.CodeLyser.CoderPad_UserService.Model.UserRole;
import com.CodeLyser.CoderPad_UserService.Respository.UserRepository;
import com.CodeLyser.CoderPad_UserService.Respository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    //registering a new user
    public User registerUser(String userName, String email, String rawPassword, String roleName){
        if(userRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("User with given email already exists");//user already present
        }

        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setActive(roleName.equals("Candidate") );
        userRepository.save(user);

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRoleName(roleName);
        userRoleRepository.save(userRole);

        return user;
    }

    //loging in the user
    public LoginResponse loginUser(String email, String rawPassword){
        Optional<User> userWithGivenEmail = userRepository.findByEmail(email);

        if(userWithGivenEmail.isEmpty()){
            throw new RuntimeException("Invalid Email or Password");
        }

        User user = userWithGivenEmail.get();
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        UserRole role = userRoleRepository.findByUserEmail(email);
        String roleName = role.getRoleName();
        String jwtToken = jwtService.generateToken(user);
        Long id = user.getId();


        return new LoginResponse(jwtToken, roleName, id);
    }

    @Transactional
    public User getUserProfile(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        return userOptional.get();  // Returning the user object directly
    }

    //updating the user details
    public String updateUser(long id, String userName, String email) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setUserName(userName);
            //existingUser.setEmail(email);
            // You can update other fields if necessary
            userRepository.save(existingUser);
            return "Success";
        }
        return "User not found";
    }

    // Method to update user password
    public String updateUserPassword(long userID, String newPassword) {
        User existingUser = userRepository.findById(userID).orElse(null);
        if (existingUser != null) {
            // Validate if the current password matches
                existingUser.setPassword(newPassword); // Save the new password, ideally hash it before saving
                userRepository.save(existingUser);
                return "Success";

        }
        return "User not found";
    }

    //getting the list of users not approved
    public List<User> getUsersNotApproved(){
        List<User> inactiveUsers = userRepository.findByIsActiveFalse();
        List<User> unapprovedUsers = new ArrayList<>();
        for (User user : inactiveUsers) {
            UserRole role = user.getUserRole();
            if (role != null && !role.getRoleName().equalsIgnoreCase("Candidate")) {
                unapprovedUsers.add(user);
            }
        }

        return unapprovedUsers;
    }

    //approve a particular user
    public String approveUser(String userEmail){
        Optional<User> userOpt = userRepository.findByEmail(userEmail);
        if(userOpt.isEmpty()){
            return "user not found";
        }

        User user = userOpt.get();
        user.setActive(true);
        userRepository.save(user);
        return "user approved successfully";
    }

    //fetching all users
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Transactional
    public boolean deleteUserById(Long id) {
        try {
            Optional<User> userOptional = userRepository.findById(id); // Get Optional of User

            if (userOptional.isPresent()) {  // Check if user exists
                User user = userOptional.get();
                // Delete the associated user role
                userRoleRepository.deleteByUserId(user.getId());  // Ensure this is correct in your repository

                // Delete the user from the repository
                userRepository.deleteById(user.getId());

                return true;  // Successfully deleted
            } else {
                return false;  // User not found
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
            return false;  // User not deleted
        }
    }


}
