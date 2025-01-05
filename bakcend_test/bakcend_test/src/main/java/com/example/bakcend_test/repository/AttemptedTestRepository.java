package com.example.bakcend_test.repository;


import com.example.bakcend_test.model.AttemptedTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttemptedTestRepository extends JpaRepository<AttemptedTest, String> {
    // Additional custom query methods can be defined here if needed
}
