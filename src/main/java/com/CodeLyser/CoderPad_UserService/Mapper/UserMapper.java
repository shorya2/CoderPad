package com.CodeLyser.CoderPad_UserService.Mapper;

import com.CodeLyser.CoderPad_UserService.Model.User;
import com.CodeLyser.CoderPad_UserService.Model.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // Converts registration data into a User entity
    public User toUser(String firstName, String lastName, String email, String password,String roleName) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setActive(roleName.equals("user"));
        return user;
    }

    // Converts the User and roleName into a UserRole entity
    public UserRole toUserRole(User user, String roleName) {
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRoleName(roleName);
        return userRole;
    }

    // Converts update data into a User entity (for updating user profile)
    public User toUpdatedUser(User existingUser, String email, String firstName, String lastName) {
        existingUser.setEmail(email);
        existingUser.setFirstName(firstName);
        existingUser.setLastName(lastName);
        return existingUser;
    }

    // Converts password change data into a User entity
    public User toUpdatedPassword(User existingUser, String email, String newPassword) {
        existingUser.setEmail(email);
        existingUser.setPassword(newPassword);
        return existingUser;
    }
}
