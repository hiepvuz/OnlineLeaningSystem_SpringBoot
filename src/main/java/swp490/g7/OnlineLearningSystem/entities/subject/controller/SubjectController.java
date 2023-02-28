package swp490.g7.OnlineLearningSystem.entities.subject.controller;

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
import swp490.g7.OnlineLearningSystem.annotations.UserPermission;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.request.SubjectRequestDto;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.response.SubjectHeaderResponse;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.response.SubjectResponseDto;
import swp490.g7.OnlineLearningSystem.entities.subject.service.SubjectService;
import swp490.g7.OnlineLearningSystem.utilities.Constants;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/all")
    public PaginationResponse getAll(Pageable pageable) {
        return subjectService.getAll(pageable);
    }

    @GetMapping("/all-manager")
    public Set<Object> getAllManager() {
        return subjectService.getAllManager();
    }

    @GetMapping("/all-expert")
    public Set<Object> getAllExpert() {
        return subjectService.getAllExpert();
    }

    @GetMapping("/{id}")
    public SubjectResponseDto getById(@PathVariable Long id) {
        return subjectService.getById(id);
    }

    @UserPermission(name = Constants.SUBJECT_DETAILS, type = Constants.ADD)
    @PostMapping("")
    public SubjectResponseDto create(@Valid @RequestBody SubjectRequestDto subjectRequestDto) {
        return subjectService.create(subjectRequestDto);
    }

    @UserPermission(name = Constants.SUBJECT_DETAILS, type = Constants.UPDATE)
    @PutMapping("/{id}")
    public SubjectResponseDto update(@PathVariable Long id, @Valid @RequestBody SubjectRequestDto subjectResponseDto) {
        return subjectService.update(id, subjectResponseDto);
    }

    @UserPermission(name = Constants.SUBJECT_DETAILS, type = Constants.DELETE)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        subjectService.deleteById(id);
    }

    @GetMapping("/filter")
    public PaginationResponse filter(@RequestParam(required = false) String subjectName,
                                     @RequestParam(required = false) String subjectCode,
                                     @RequestParam(required = false) Boolean status,
                                     Pageable pageable) {
        return subjectService.filter(subjectName, subjectCode, status, pageable);
    }

    @UserPermission(name = Constants.SUBJECT_DETAILS, type = Constants.UPDATE)
    @PutMapping("/{id}/enable")
    public void enable(@PathVariable("id") Long id) {
        subjectService.enable(id);
    }

    @UserPermission(name = Constants.SUBJECT_DETAILS, type = Constants.UPDATE)
    @PutMapping("/{id}/disable")
    public void disable(@PathVariable("id") Long id) {
        subjectService.disable(id);
    }

    @GetMapping("/header")
    public List<SubjectHeaderResponse> getSubjectByRole() {
        return subjectService.getSubjectByRole();
    }
}
