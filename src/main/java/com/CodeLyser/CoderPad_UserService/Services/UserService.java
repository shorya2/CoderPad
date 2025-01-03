package com.CodeLyser.CoderPad_UserService.Services;

import com.CodeLyser.CoderPad_UserService.Mapper.UserMapper;
import com.CodeLyser.CoderPad_UserService.Model.User;
import com.CodeLyser.CoderPad_UserService.Model.UserRole;
import com.CodeLyser.CoderPad_UserService.Respository.UserRepository;
import com.CodeLyser.CoderPad_UserService.Respository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserMapper userMapper;


    //registering a new user
    public String registerUser(String firstName, String lastName, String email, String password, String roleName){
        if(userRepository.findByEmail(email).isPresent()){
            return ("User with given email already exists");//user already present
        }

        User user = userMapper.toUser(firstName, lastName, email, password, roleName);
        userRepository.save(user);

        UserRole userRole = userMapper.toUserRole(user, roleName);
        userRoleRepository.save(userRole);

        return "User registered successfully, awaiting approval for role: " + roleName;
    }

    //loging in the user
    public String loginUser(String email, String password){
        Optional<User> userWithGivenEmail = userRepository.findByEmail(email);

        if(userWithGivenEmail.isEmpty()){
            return "Invalid Email or Password";
        }

        User user = userWithGivenEmail.get();
        if(!user.getPassword().equals(password)){
            return "Password Do not match";
        }
        return user.isActive()? "Login successfully": "User not approved";
    }

    //updating the user details
    public String updateUser(Long id, String email, String firstName, String lastName){
        Optional<User> userWithGivenId = userRepository.findById(id);
        if(userWithGivenId.isEmpty()){
            return "User not found";
        }
        User user = userWithGivenId.get();
        userMapper.toUpdatedUser(user,email,firstName,lastName);
        userRepository.save(user);
        return "Profile updated successfully";
    }

    //changing the user password
    public String updateUserPassword(Long userID,String email, String currentPassword, String newPassword){
        Optional<User> userWithGivenID = userRepository.findById(userID);
        if(userWithGivenID.isEmpty()){
            return "User not found";
        }
        User user = userWithGivenID.get();

        if(!user.getPassword().equals(currentPassword)){
            return "current password does not match";
        }
        userMapper.toUpdatedPassword(user, email, newPassword);
        userRepository.save(user);
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
        return "user approved successfully";
    }

    //fetching all users
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

}
