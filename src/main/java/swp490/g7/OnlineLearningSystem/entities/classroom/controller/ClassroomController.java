package swp490.g7.OnlineLearningSystem.entities.classroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import swp490.g7.OnlineLearningSystem.annotations.UserPermission;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.request.ClassroomRequestDto;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.response.ClassroomResponseDto;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.response.TraineeResponse;
import swp490.g7.OnlineLearningSystem.entities.classroom.service.ClassroomService;
import swp490.g7.OnlineLearningSystem.utilities.Constants;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import javax.validation.Valid;

@RestController
@RequestMapping("/class")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @UserPermission(name = Constants.CLASS_LIST, type = Constants.ALL)
    @GetMapping("/all")
    public PaginationResponse findAll(Pageable pageable) {
        return classroomService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ClassroomResponseDto getClassroomById(@PathVariable Long id) {
        return classroomService.getClassroomById(id);
    }

    @UserPermission(name = Constants.CLASS_DETAILS, type = Constants.ADD)
    @PostMapping("")
    public ClassroomResponseDto create(@Valid @RequestBody ClassroomRequestDto request) {
        return classroomService.save(request);
    }

    @UserPermission(name = Constants.CLASS_DETAILS, type = Constants.UPDATE)
    @PutMapping("/{id}")
    public ClassroomResponseDto update(@PathVariable Long id, @Valid @RequestBody ClassroomRequestDto request) {
        return classroomService.update(id, request);
    }

    @UserPermission(name = Constants.CLASS_LIST, type = Constants.ALL)
    @GetMapping("/filter")
    public PaginationResponse filter(@RequestParam("subjectId") Long subjectId,
                                     @RequestParam(value = "status", required = false) Boolean status,
                                     @RequestParam(value = "fromDate", required = false) String fromDate,
                                     @RequestParam(value = "toDate", required = false) String toDate,
                                     @RequestParam(value = "classCode", required = false) String classCode,
                                     @RequestParam(value = "trainerUserName", required = false) String trainerUserName,
                                     @RequestParam(value = "supporterUserName", required = false) String supporterUserName,
                                     @RequestParam(value = "branch", required = false) String branch,
                                     @RequestParam(value = "term", required = false) String term,
                                     Pageable pageable) {
        return classroomService.filter(status, fromDate, toDate, classCode, trainerUserName, supporterUserName, term,
                branch, subjectId, pageable);
    }

    @UserPermission(name = Constants.CLASS_DETAILS, type = Constants.UPDATE)
    @PutMapping("/{id}/enable")
    public void enable(@PathVariable("id") Long id) {
        classroomService.enable(id);
    }

    @UserPermission(name = Constants.CLASS_DETAILS, type = Constants.UPDATE)
    @PutMapping("/{id}/disable")
    public void disable(@PathVariable("id") Long id) {
        classroomService.disable(id);
    }

    @PostMapping("/import-trainee/{classId}")
    public TraineeResponse uploadFile(@PathVariable("classId") Long classId, @RequestParam("file") MultipartFile file) {
        return classroomService.uploadFile(classId, file);
    }
}