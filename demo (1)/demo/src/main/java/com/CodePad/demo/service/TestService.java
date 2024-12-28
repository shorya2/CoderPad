package com.CodePad.demo.service;
import com.CodePad.demo.dto.TestRequestDTO;
import com.CodePad.demo.entity.TestMaster;
import com.CodePad.demo.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class  TestService {

    @Autowired
    private TestRepository testRepository;

    public TestMaster createTest(TestRequestDTO testRequest) {
        TestMaster test = new TestMaster();
        test.setTestName(testRequest.getTestName());
        test.setDescription(testRequest.getDescription());
        test.setCreatedBy(testRequest.getCreatedBy());
        return testRepository.save(test);
    }

    public List<TestMaster> getTestsByCreator(String createdBy) {
        return testRepository.findByCreatedBy(createdBy);
    }

    public TestMaster modifyTest(Long testId, TestRequestDTO testRequest, String userId) {
        TestMaster test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found"));
        if (!test.getCreatedBy().equals(userId)) {
            throw new RuntimeException("Unauthorized to modify this test");
        }
        test.setTestName(testRequest.getTestName());
        test.setDescription(testRequest.getDescription());
        return testRepository.save(test);
    }
}
