package swp490.g7.OnlineLearningSystem.entities.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.ClassLesson;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.Lesson;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.TestFilterDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.TestTypeDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.TestResultReview;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.other.TestHistoryDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.ResultTestDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.TestRandomResponseDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.request.TestRequestDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.response.TestResponseCustomDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.response.TestResponseDto;
import swp490.g7.OnlineLearningSystem.entities.test.service.TestService;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/filter")
    public PaginationResponse filter(@RequestParam("subjectId") Long subjectId,
                                     @RequestParam(value = "testType", required = false) Integer testType,
                                     @RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "status", required = false) Boolean status,
                                     Pageable pageable) {
        return testService.filter(subjectId, testType, name, status, pageable);
    }

    @GetMapping("/subject")
    public PaginationResponse findAllBySubject(@RequestParam("subjectId") Long id, Pageable pageable) {
        return testService.getAllBySubject(id, pageable);
    }

    @GetMapping("/{id}")
    public TestResponseCustomDto getById(@PathVariable Long id) {
        return testService.findById(id, Lesson.LESSON_QUIZ_TYPE);
    }

    @PostMapping("")
    public TestResponseDto create(@Valid @RequestBody TestRequestDto request) {
        return testService.create(request);
    }

    @PutMapping("/{id}")
    public TestResponseDto update(@PathVariable Long id, @Valid @RequestBody TestRequestDto request) {
        return testService.update(id, request);
    }

    @PutMapping("/{id}/disable")
    public void disable(@PathVariable Long id) {
        testService.disable(id);
    }

    @PutMapping("/{id}/enable")
    public void enable(@PathVariable Long id) {
        testService.enable(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        testService.delete(id);
    }

    @GetMapping("/all")
    public List<TestTypeDto> getAllTestTypeWithTest(@RequestParam("subjectId") Long subjectId) {
        return testService.getAllTestTypeWithTest(subjectId);
    }

    @GetMapping("/test-type")
    public List<TestFilterDto> getAllTestByTestType(@RequestParam("subjectId") Long subjectId,
                                                    @RequestParam("testType") Integer testType) {
        return testService.getAllTestByTestType(subjectId, testType);
    }

    @GetMapping("/history")
    public List<TestHistoryDto> getTestHistory(@RequestParam("subjectId") Long subjectId,
                                               @RequestParam(value = "name", required = false) String name) {
        return testService.getTestHistory(subjectId, name);
    }

    @GetMapping("{id}/practice-quiz")
    public TestRandomResponseDto getPractice(@PathVariable Long id) {
        return testService.getPractice(id);
    }

    @PostMapping("/{id}/practice-quiz-submit")
    public ResultTestDto submitQuizAndPractice(@PathVariable Long id,
                                               @RequestBody TestRandomResponseDto request) {
        return testService.getAnswerQuizAndPractice(id, request, Lesson.LESSON_QUIZ_TYPE);
    }

    @GetMapping("/quiz-lesson")
    public List<TestFilterDto> getAllTestForQuizLesson(@RequestParam("subjectId") Long subjectId) {
        return testService.getAllTestForQuizLesson(subjectId);
    }

    @GetMapping("/{id}/simulation-demo")
    public TestRandomResponseDto getSimulation(@PathVariable Long id) {
        return testService.getSimulationAndDemo(id);
    }

    @PostMapping("/{id}/simulation-demo-submit")
    public ResultTestDto submitSimulationAndDemo(@PathVariable Long id,
                                                 @RequestBody TestRandomResponseDto request) {
        return testService.getAnswerSimulationAndDemo(id, request);
    }

    @GetMapping("/{id}/test-review")
    public TestResultReview getResultTest(@PathVariable Long id) {
        return testService.getResultTest(id, Lesson.LESSON_QUIZ_TYPE);
    }

    @PostMapping("/{id}/class-quiz-submit")
    public ResultTestDto submitClassQuiz(@PathVariable Long id,
                                         @RequestBody TestRandomResponseDto request) {
        return testService.getAnswerQuizAndPractice(id, request, ClassLesson.CLASS_LESSON_QUIZ_TYPE);
    }

    @GetMapping("/{id}/class-test-review")
    public TestResultReview getClassResultTest(@PathVariable Long id) {
        return testService.getResultTest(id, ClassLesson.CLASS_LESSON_QUIZ_TYPE);
    }

    @GetMapping("{id}/class")
    public TestResponseCustomDto getTestClassById(@PathVariable Long id) {
        return testService.findById(id, ClassLesson.CLASS_LESSON_QUIZ_TYPE);
    }
}
