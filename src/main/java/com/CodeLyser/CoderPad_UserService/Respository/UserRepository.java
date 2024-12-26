package com.CodeLyser.CoderPad_UserService.Respository;

import com.CodeLyser.CoderPad_UserService.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    List<User> findByIsActiveFalse();
    List<User> findAll();

}
