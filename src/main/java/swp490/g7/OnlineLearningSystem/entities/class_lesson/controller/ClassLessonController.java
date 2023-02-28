package swp490.g7.OnlineLearningSystem.entities.class_lesson.controller;

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
import swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.dto.ClassLessonModuleDto;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.request.ClassLessonRequestDto;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.response.ClassLessonResponseDto;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.service.ClassLessonService;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/class-lesson")
public class ClassLessonController {
    @Autowired
    private ClassLessonService classLessonService;

    @GetMapping("/filter")
    public List<ClassLessonModuleDto> findAll(@RequestParam("subjectId") Long subjectId,
                                              @RequestParam(value = "classId", required = false) Long classId,
                                              @RequestParam(value = "moduleId", required = false) Long moduleId,
                                              @RequestParam(value = "status", required = false) Boolean status,
                                              @RequestParam(value = "typeLesson", required = false) String typeLesson,
                                              @RequestParam(value = "name", required = false) String name) {
        return classLessonService.filter(subjectId, classId, moduleId, status, typeLesson, name);
    }

    @PostMapping("")
    public ClassLessonResponseDto create(@Valid @RequestBody ClassLessonRequestDto request) throws IOException {
        return classLessonService.create(request);
    }

    @PutMapping("/{id}")
    public ClassLessonResponseDto update(@PathVariable Long id, @Valid @RequestBody ClassLessonRequestDto request) {
        return classLessonService.update(id, request);
    }

    @GetMapping("/{id}")
    public ClassLessonResponseDto findById(@PathVariable Long id) {
        return classLessonService.findById(id);
    }

    @GetMapping("/class-module")
    public PaginationResponse getAllByClassModule(@RequestParam("subjectId") Long subjectId, Pageable pageable) {
        return classLessonService.getAllByClassModule(subjectId, pageable);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        classLessonService.delete(id);
    }

    @PutMapping("/{id}/enable")
    public void enable(@PathVariable Long id) {
        classLessonService.enable(id);
    }

    @PutMapping("/{id}/disable")
    public void disable(@PathVariable Long id) {
        classLessonService.disable(id);
    }

    @PostMapping("/{id}/submit")
    public void normalLessonSubmit(@PathVariable Long id) {
        classLessonService.submit(id);
    }
}
