package com.CodeLyser.CoderPad_UserService.Services;

import com.CodeLyser.CoderPad_UserService.Model.LoginResponse;
import com.CodeLyser.CoderPad_UserService.Model.User;
import com.CodeLyser.CoderPad_UserService.Model.UserRole;
import com.CodeLyser.CoderPad_UserService.Respository.UserRepository;
import com.CodeLyser.CoderPad_UserService.Respository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    public User registerUser(String firstName, String lastName, String email, String password, String roleName){
        if(userRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("User with given email already exists");//user already present
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setActive(roleName.equals("user") );
        userRepository.save(user);

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRoleName(roleName);
        userRoleRepository.save(userRole);

        return user;
    }

    //loging in the user
    public LoginResponse loginUser(String email, String password){
        Optional<User> userWithGivenEmail = userRepository.findByEmail(email);

        if(userWithGivenEmail.isEmpty()){
            throw new RuntimeException("Invalid Email or Password");
        }

        User user = userWithGivenEmail.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        UserRole role = userRoleRepository.findByUserEmail(email);
        String roleName = role.getRoleName();
        String jwtToken = jwtService.generateToken(user);


        return new LoginResponse(jwtToken, roleName);
    }

    //updating the user details
    public String updateUser(Long id, String email, String firstName, String lastName){
        Optional<User> userWithGivenId = userRepository.findById(id);
        if(userWithGivenId.isEmpty()){
            return "User not found";
        }
        User user = userWithGivenId.get();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return "Profile updated successfully";
    }

    //changing the user password
    public String updateUserPassword(Long userID, String currentPassword, String newPassword){
        Optional<User> userWithGivenID = userRepository.findById(userID);
        if(userWithGivenID.isEmpty()){
            return "User not found";
        }
        User user = userWithGivenID.get();

        if(!user.getPassword().equals(currentPassword)) {
            return "current password does not match";
        }
        user.setPassword(newPassword);
        return "Password updated successfully";
    }

    //getting the list of users not approved
    public List<User> getUsersNotApproved(){
        List<User> inactiveUsers = userRepository.findByIsActiveFalse();
        return inactiveUsers;
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

}
