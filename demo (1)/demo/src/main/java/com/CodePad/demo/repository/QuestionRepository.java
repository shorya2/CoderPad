package com.CodePad.demo.repository;


import com.CodePad.demo.entity.QuestionMaster;
import com.CodePad.demo.entity.TestMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionMaster, Long> {
    List<QuestionMaster> findByTest(TestMaster test);
}
