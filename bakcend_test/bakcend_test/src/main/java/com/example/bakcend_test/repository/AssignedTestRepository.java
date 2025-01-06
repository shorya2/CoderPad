package com.example.bakcend_test.repository;

import com.example.bakcend_test.model.AssignedTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignedTestRepository extends JpaRepository<AssignedTest, String> {
    // Additional custom query methods can be defined here if needed
}
