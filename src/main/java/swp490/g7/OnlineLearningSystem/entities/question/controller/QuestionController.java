package swp490.g7.OnlineLearningSystem.entities.question.controller;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import swp490.g7.OnlineLearningSystem.entities.question.domain.dto.QuestionExportDto;
import swp490.g7.OnlineLearningSystem.entities.question.domain.request.QuestionRequestDto;
import swp490.g7.OnlineLearningSystem.entities.question.domain.response.QuestionResponseDto;
import swp490.g7.OnlineLearningSystem.entities.question.service.QuestionService;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/{id}")
    public QuestionResponseDto findById(@PathVariable Long id) {
        return questionService.findById(id);
    }

    @GetMapping("/filter")
    public PaginationResponse filter(@RequestParam(value = "subjectId") Long subjectId,
                                     @RequestParam(value = "testId", required = false) Long testId,
                                     @RequestParam(value = "lessonId", required = false) Long lessonId,
                                     @RequestParam(value = "body", required = false) String body,
                                     @RequestParam(value = "groupId", required = false) Long groupId,
                                     Pageable pageable) {
        return questionService.filter(subjectId, testId, lessonId, body, groupId, pageable);
    }

    @PostMapping("")
    public QuestionResponseDto create(@Valid @RequestBody QuestionRequestDto request) {
        return questionService.create(request);
    }

    @PutMapping("/{id}")
    public QuestionResponseDto update(@PathVariable Long id, @Valid @RequestBody QuestionRequestDto request) {
        return questionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        questionService.delete(id);
    }

    @PutMapping("/{id}/image-upload")
    public String imageUpload(@PathVariable Long id, @RequestPart(value = "file") MultipartFile file) {
        return questionService.imageUpload(id, file);
    }

    @PutMapping("/import-question")
    public void importQuestion(@RequestParam("subjectId") Long subjectId,
                               @RequestParam(value = "testId", required = false) Long testId,
                               @RequestParam(value = "lessonId", required = false) Long lessonId,
                               @RequestParam(value = "classLessonId", required = false) Long classLessonId,
                               @RequestPart(value = "zip") MultipartFile zip) throws IOException {
        questionService.importQuestion(subjectId, lessonId, classLessonId, testId, zip);
    }

    @GetMapping("/export-question")
    public List<QuestionExportDto> exportQuestion(@RequestParam(value = "subjectId") Long subjectId,
                                                  @RequestParam(value = "testId", required = false) Long testId,
                                                  @RequestParam(value = "lessonId", required = false) Long lessonId,
                                                  @RequestParam(value = "body", required = false) String body,
                                                  @RequestParam(value = "groupId", required = false) Long groupId) {
        return questionService.exportQuestion(subjectId, testId, lessonId, body, groupId);
    }
}
