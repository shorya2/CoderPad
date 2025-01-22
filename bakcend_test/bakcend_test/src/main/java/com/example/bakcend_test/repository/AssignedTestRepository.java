package com.example.bakcend_test.repository;

import com.example.bakcend_test.model.AssignedTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
    public interface AssignedTestRepository extends JpaRepository<AssignedTest, String> {
    // Custom method to delete by email and createdBy
    void deleteByEmailAndCreatedBy(String email, String createdBy);
    List<AssignedTest> findByEmail(String email);

//    @Modifying
//    @Query("DELETE FROM AssignedTest at WHERE at.email = :email AND at.createdBy = :createdBy")
//    void deleteByEmailAndCreatedBy(@Param("email") String email, @Param("createdBy") String createdBy);

    // Optional: Find by email and createdBy to check if the test exists
    Optional<AssignedTest> findByEmailAndCreatedBy(String email, String createdBy);
    }
