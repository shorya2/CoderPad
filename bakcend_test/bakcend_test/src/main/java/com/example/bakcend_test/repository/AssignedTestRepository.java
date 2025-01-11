package com.example.bakcend_test.repository;

import com.example.bakcend_test.model.AssignedTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
    public interface AssignedTestRepository extends JpaRepository<AssignedTest, String> {
    // Custom method to delete by email and createdBy
    void deleteByEmailAndCreatedBy(String email, String createdBy);

    // Optional: Find by email and createdBy to check if the test exists
    Optional<AssignedTest> findByEmailAndCreatedBy(String email, String createdBy);
    }
