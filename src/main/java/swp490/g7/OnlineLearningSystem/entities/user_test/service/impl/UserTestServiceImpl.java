package swp490.g7.OnlineLearningSystem.entities.user_test.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.other.TestHistoryDto;
import swp490.g7.OnlineLearningSystem.entities.user_test.domain.UserTest;
import swp490.g7.OnlineLearningSystem.entities.user_test.repository.UserTestRepository;
import swp490.g7.OnlineLearningSystem.entities.user_test.service.UserTestService;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.Optional;

@Service
public class UserTestServiceImpl implements UserTestService {
    private static final Logger logger = LogManager.getLogger(UserTestServiceImpl.class);
    @Autowired
    private UserTestRepository userTestRepository;

    @Override
    public PaginationResponse filter(Long subjectId, Long userId, Integer testType, String testName, Pageable pageable) {
        PagedListHolder pagedListHolder =
                new PagedListHolder(userTestRepository.filter(subjectId, userId, testType, testName));
        pagedListHolder.setPage(pageable.getPageNumber());
        pagedListHolder.setPageSize(pageable.getPageSize());

        return PaginationResponse.builder()
                .total(pagedListHolder.getSource().size())
                .numberOfPage(pagedListHolder.getPageCount())
                .pageIndex(pageable.getPageNumber())
                .items(pagedListHolder.getPageList())
                .build();
    }

    @Override
    public TestHistoryDto findById(Long id) {
        Optional<UserTest> userTest = userTestRepository.findById(id);
        if (!userTest.isPresent()) {
            logger.error("User Test not found with id: {}", id);
            throw new OnlineLearningException(ErrorTypes.USER_TEST_NOT_FOUND, id.toString());
        }
        return mapUserTest(userTest.get());
    }

    private TestHistoryDto mapUserTest(UserTest userTest) {
        TestHistoryDto testHistory = new TestHistoryDto();
        testHistory.setUserTestId(userTest.getUserTestId());
        testHistory.setTestId(userTest.getTest().getTestId());
        testHistory.setName(userTest.getTest().getName());
        testHistory.setDuration(userTest.getTest().getDuration());
        testHistory.setCorrects(userTest.getCorrects());
        testHistory.setResult(userTest.getScorePercent());
        testHistory.setPassRate(userTest.getTest().getPassRate());
        testHistory.setNumberOfQuestion(userTest.getTotal());
        testHistory.setIsPassed(checkPassed(userTest.getScorePercent(), userTest.getTest().getPassRate()));
        return testHistory;
    }

    private Boolean checkPassed(Double result, Integer passRate) {
        return result >= (double) passRate;
    }
}
