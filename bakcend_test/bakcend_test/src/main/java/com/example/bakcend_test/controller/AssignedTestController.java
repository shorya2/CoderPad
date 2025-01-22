package com.example.bakcend_test.controller;

import com.example.bakcend_test.model.AssignedTest;
import com.example.bakcend_test.model.AssignedTestsWrapper;
import com.example.bakcend_test.service.AssignedTestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/assigned-tests")
public class AssignedTestController {

    @Autowired
    private AssignedTestService assignedTestService;

    // Fetch all assigned tests
    @GetMapping
    public List<AssignedTest> getAllAssignedTests() {
        return assignedTestService.getAllAssignedTests();
    }

    // Fetch an assigned test by ID
    @GetMapping("/{id}")
    public Optional<AssignedTest> getAssignedTestById(@PathVariable String id) {
        return assignedTestService.getAssignedTestById(id);
    }

    // Assign a new test
    @PostMapping
    public List<AssignedTest> assignTest(@RequestBody List<AssignedTest> assignedTests) {
        System.out.println(assignedTests);
        //return null;
        return assignedTestService.assignTest(assignedTests);
    }
//    @PostMapping
//    public List<AssignedTest> assignTests(@RequestBody List<AssignedTest> assignedTests) {
//        return assignedTestService.assignTest(assignedTests);
//    }

//    @PostMapping
//    public List<AssignedTest> assignTests(@RequestBody AssignedTestsWrapper wrapper) {
//
//        if (wrapper == null || wrapper.getAssignedTests() == null) {
//            throw new IllegalArgumentException("Invalid input: AssignedTestsWrapper or assignedTests list is null.");
//        }
//        List<AssignedTest> assignedTests = wrapper.getAssignedTests();
//        List<AssignedTest> savedTests = new ArrayList<>();
//
//        for (AssignedTest assignedTest : assignedTests) {
//            AssignedTest savedTest = assignedTestService.assignTest(assignedTest);
//            savedTests.add(savedTest);
//        }
//
//        return savedTests;
//    }

//    @PostMapping
//    public List<AssignedTest> assignTests(@RequestBody List<AssignedTest> assignedTests) {
//        List<AssignedTest> result = new ArrayList<>();
//
//        for (AssignedTest assignedTest : assignedTests) {
//            if (assignedTest.getEmail() != null) {
//                result.add(assignedTestService.assignTest(assignedTest));
//            } else {
//                throw new IllegalArgumentException("Email cannot be null for any test assignment.");
//            }
//        }
//        return result;
//    }

//    @PostMapping
//    public ResponseEntity<?> assignTests(@RequestBody List<AssignedTest> assignedTests) {
//        try {
//            if (assignedTests.isEmpty()) {
//                return ResponseEntity.badRequest().body("No test assignments provided.");
//            }
//
//            // Save all assigned tests
//            List<AssignedTest> savedTests = assignedTestService.saveAll(assignedTests);
//            return ResponseEntity.ok(savedTests);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error while processing test assignments: " + e.getMessage());
//        }
//    }

//    @PostMapping
//    public ResponseEntity<?> assignTests(@RequestBody Object input) {
//        try {
//            List<AssignedTest> assignedTests;
//
//            if (input instanceof List) {
//                // Input is already a List
//                assignedTests = (List<AssignedTest>) input;
//            } else {
//                // Input is a single object, wrap it in a List
//                AssignedTest singleTest = new ObjectMapper().convertValue(input, AssignedTest.class);
//                assignedTests = Collections.singletonList(singleTest);
//            }
//
//            List<AssignedTest> savedTests = assignedTestService.saveAll(assignedTests);
//            return ResponseEntity.ok(savedTests);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body("Invalid JSON structure: " + e.getMessage());
//        }
//    }


    // Remove an assigned test
    @DeleteMapping("/{id}")
    public void removeAssignedTest(@PathVariable String id) {
        assignedTestService.removeAssignedTest(id);
    }

    @DeleteMapping("/by-email-and-createdBy")
    public void removeAssignedTestByEmailAndCreatedBy(@RequestParam String email, @RequestParam String createdBy) {
        assignedTestService.removeAssignedTestByEmailAndCreatedBy(email, createdBy);
    }

    // Fetch assigned tests by email
    @GetMapping("/assigned-tests/{email}")
    public List<AssignedTest> getAssignedTestsByEmail(@PathVariable String email) {
        return assignedTestService.getAssignedTestsByEmail(email);
    }

}
