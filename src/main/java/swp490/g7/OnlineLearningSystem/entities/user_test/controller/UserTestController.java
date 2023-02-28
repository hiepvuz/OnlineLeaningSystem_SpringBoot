package swp490.g7.OnlineLearningSystem.entities.user_test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.other.TestHistoryDto;
import swp490.g7.OnlineLearningSystem.entities.user_test.service.UserTestService;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

@RestController
@RequestMapping("/user-test")
public class UserTestController {

    @Autowired
    private UserTestService userTestService;

    @GetMapping("/filter")
    public PaginationResponse getAllTestHistory(@RequestParam("subjectId") Long subjectId,
                                                @RequestParam("userId") Long userId,
                                                @RequestParam(value = "testType", required = false) Integer testType,
                                                @RequestParam(value = "testName", required = false) String testName,
                                                Pageable pageable) {

        return userTestService.filter(subjectId, userId, testType, testName, pageable);
    }

    @GetMapping("/{id}")
    public TestHistoryDto findById(@PathVariable Long id) {
        return userTestService.findById(id);
    }
}
