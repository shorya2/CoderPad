package com.CodeLyser.CoderPad_UserService.Respository;

import com.CodeLyser.CoderPad_UserService.Model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUserId(Long id);

    List<UserRole> findByRoleName(String role);

    UserRole findByUserEmail(String email);

    void deleteByUserId(Long userId);
}