package com.CodePad.demo.repository;

import com.CodePad.demo.entity.TestMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<TestMaster, Long> {
    List<TestMaster> findByCreatedBy(String createdBy);
}
