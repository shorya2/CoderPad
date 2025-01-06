package com.example.bakcend_test.repository;

import com.example.bakcend_test.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, String> {
    // Additional custom query methods can be defined here if needed
}
