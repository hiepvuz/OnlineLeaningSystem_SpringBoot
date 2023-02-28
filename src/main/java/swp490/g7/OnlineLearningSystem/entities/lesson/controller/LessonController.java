package swp490.g7.OnlineLearningSystem.entities.lesson.controller;

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
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.dto.LessonConfigTestDto;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.dto.LessonModuleDto;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.request.LessonRequestDto;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.response.LessonResponseDto;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.response.NormalLessonResponse;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.response.QuizLessonResponse;
import swp490.g7.OnlineLearningSystem.entities.lesson.service.LessonService;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lesson")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @GetMapping("/filter")
    public List<LessonModuleDto> filter(@RequestParam("subjectId") Long subjectId,
                                        @RequestParam(value = "testId", required = false) Long testId,
                                        @RequestParam(value = "moduleId", required = false) Long moduleId,
                                        @RequestParam(value = "status", required = false) Boolean status,
                                        @RequestParam(value = "typeLesson", required = false) String typeLesson,
                                        @RequestParam(value = "name", required = false) String name,
                                        Pageable pageable) {
        return lessonService.filter(subjectId, testId, moduleId, status, typeLesson, name, pageable);
    }

    @PostMapping("")
    public LessonResponseDto create(@Valid @RequestBody LessonRequestDto request) {
        return lessonService.create(request);
    }

    @PutMapping("/{id}")
    public LessonResponseDto update(@PathVariable Long id, @Valid @RequestBody LessonRequestDto request) {
        return lessonService.update(id, request);
    }

    @GetMapping("/{id}")
    public LessonResponseDto findById(@PathVariable Long id) {
        return lessonService.findById(id);
    }

    @GetMapping("/normal")
    public PaginationResponse getAllBySubjectModule(@RequestParam("subjectId") Long subjectId,
                                                    @RequestParam("moduleId") Long moduleId,
                                                    Pageable pageable) {
        return lessonService.getAllBySubjectModule(subjectId, moduleId, pageable);
    }

    @GetMapping("/all")
    public List<LessonConfigTestDto> getAllForTestConfig(@RequestParam("subjectId") Long subjectId,
                                                         @RequestParam("moduleId") Long moduleId) {
        return lessonService.getAllForTestConfig(subjectId, moduleId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        lessonService.delete(id);
    }

    @PutMapping("/{id}/enable")
    public void enable(@PathVariable Long id) {
        lessonService.enable(id);
    }

    @PutMapping("/{id}/disable")
    public void disable(@PathVariable Long id) {
        lessonService.disable(id);
    }

    @PostMapping("/{id}/submit")
    public void normalLessonSubmit(@PathVariable Long id) {
        lessonService.submit(id);
    }

    @GetMapping("/all-normal")
    public List<NormalLessonResponse> getAllNormalLessonBySubjectId(@RequestParam("subjectId") Long subjectId) {
        return lessonService.getAllNormalLessonBySubjectId(subjectId);
    }

    @GetMapping("/all-quiz")
    public List<QuizLessonResponse> getAllQuizLessonBySubjectId(@RequestParam("subjectId") Long subjectId) {
        return lessonService.getAllQuizLessonBySubjectId(subjectId);
    }
}
