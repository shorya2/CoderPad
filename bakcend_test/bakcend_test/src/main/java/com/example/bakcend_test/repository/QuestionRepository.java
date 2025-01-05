package com.example.bakcend_test.repository;


//import org.aspectj.weaver.patterns.TypePatternQuestions;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface QuestionRepository extends JpaRepository<TypePatternQuestions.Question, String> {
//}

import com.example.bakcend_test.model.Question;  // Correct import for the Question model
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, String> {
}

