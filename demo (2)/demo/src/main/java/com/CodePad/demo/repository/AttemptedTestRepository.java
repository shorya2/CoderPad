package com.CodePad.demo.repository;

import com.CodePad.demo.entity.AttemptedTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttemptedTestRepository extends JpaRepository<AttemptedTest, Long> {

    List<AttemptedTest> findByTestIdAndUserId(Long testId, Long userId);

    List<AttemptedTest> findByUserId(Long userId);

    List<AttemptedTest> findByTestId(Long testId);
}
