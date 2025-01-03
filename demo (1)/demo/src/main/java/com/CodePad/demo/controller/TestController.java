    package com.CodePad.demo.controller;

    import com.CodePad.demo.dto.TestRequestDTO;
    import com.CodePad.demo.entity.TestMaster;
    import com.CodePad.demo.service.TestService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/tests")
    public class TestController {

        @Autowired
        private TestService testService;

        @PostMapping
        public TestMaster createTest(@RequestBody TestRequestDTO testRequest) {
            return testService.createTest(testRequest);
        }

        @GetMapping
        public List<TestMaster> getTestsByCreator(@RequestParam String createdBy) {
            return testService.getTestsByCreator(createdBy);
        }

        @PutMapping("/{testId}")
        public TestMaster modifyTest(@PathVariable Long testId,
                                     @RequestBody TestRequestDTO testRequest,
                                     @RequestParam String userId) {
            return testService.modifyTest(testId, testRequest, userId);
        }

    }
