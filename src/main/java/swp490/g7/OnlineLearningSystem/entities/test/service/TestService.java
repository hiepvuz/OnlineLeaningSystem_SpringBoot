package swp490.g7.OnlineLearningSystem.entities.test.service;

import org.springframework.data.domain.Pageable;
import swp490.g7.OnlineLearningSystem.entities.test.domain.Test;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.TestFilterDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.TestTypeDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.TestResultReview;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.other.TestHistoryDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.ResultTestDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.TestRandomResponseDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.request.TestRequestDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.response.TestResponseCustomDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.response.TestResponseDto;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.List;

public interface TestService {
    PaginationResponse filter(Long subjectId, Integer testType, String name, Boolean status, Pageable pageable);

    PaginationResponse getAllBySubject(Long id, Pageable pageable);

    TestResponseCustomDto findById(Long testId, Integer type);

    TestResponseDto create(TestRequestDto request);

    TestResponseDto update(Long id, TestRequestDto request);

    void delete(Long testId);

    List<TestTypeDto> getAllTestTypeWithTest(Long subjectId);

    List<TestFilterDto> getAllTestByTestType(Long subjectId, Integer testType);

    Integer countTotalQuestionInTestQuizLessonAndPracticeTest(Test test);

    Integer countTotalQuestionInTestAnotherType(Test test);

    List<TestHistoryDto> getTestHistory(Long subjectId, String name);

    TestRandomResponseDto getPractice(Long testId);

    ResultTestDto getAnswerQuizAndPractice(Long testId, TestRandomResponseDto request, Integer type);

    List<TestFilterDto> getAllTestForQuizLesson(Long subjectId);

    void saveTestResultForQuizAndPractice(Test test, ResultTestDto resultTestDto, TestRandomResponseDto testRandom,
                                          Integer type);

    TestRandomResponseDto getSimulationAndDemo(Long id);

    ResultTestDto getAnswerSimulationAndDemo(Long testId, TestRandomResponseDto request);

    void saveTestResultForSimulationAndDemo(Test test, ResultTestDto resultTestDto, TestRandomResponseDto testRandom);

    TestResultReview getResultTest(Long id, Integer type);

    void disable(Long id);

    void enable(Long id);
}
