package com.CodePad.demo.repository;

import com.CodePad.demo.entity.AssignedTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignedTestRepository extends JpaRepository<AssignedTest, Long> {
    List<AssignedTest> findByUserEmail(String userEmail);
    List<AssignedTest> findByAssignedBy(int assignedBy);
}
