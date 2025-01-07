package com.CodeLyser.CoderPad_UserService.Services;

import com.CodeLyser.CoderPad_UserService.Model.User;
import com.CodeLyser.CoderPad_UserService.Model.UserRole;
import com.CodeLyser.CoderPad_UserService.Respository.UserRepository;
import com.CodeLyser.CoderPad_UserService.Respository.UserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Load the user entity from the repository based on the email
        Optional<User> userOptional = userRepository.findByEmail(email);

        // Check if the user is present in the database
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        User user = userOptional.get();

        // Retrieve roles for the user from the UserRole table
        List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());

        // Map the roles to GrantedAuthority (if your roleName in DB doesn't have "ROLE_" prefix)
        List<GrantedAuthority> authorities = userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName())) // Assuming roleName is stored without "ROLE_" prefix
                .collect(Collectors.toList());

        // Create a UserDetails object with the email, password, and authorities
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // Username (email)
                user.getPassword(), // Password (stored in DB)
                authorities // Authorities based on roles
        );
    }

}