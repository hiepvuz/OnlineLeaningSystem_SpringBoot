package swp490.g7.OnlineLearningSystem.entities.classroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swp490.g7.OnlineLearningSystem.annotations.UserPermission;
import swp490.g7.OnlineLearningSystem.entities.classroom.service.ClassUserService;
import swp490.g7.OnlineLearningSystem.entities.classroom.service.TraineeService;
import swp490.g7.OnlineLearningSystem.utilities.Constants;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

@RestController
@RequestMapping("/trainee")
public class TraineeController {

    @Autowired
    private TraineeService traineeService;

    @Autowired
    private ClassUserService classUserService;

    @UserPermission(name = Constants.TRAINEE_LIST, type = Constants.ALL)
    @GetMapping("/all")
    public PaginationResponse findAllTrainee(@RequestParam Long classId, @RequestParam Boolean status, Pageable pageable) {
        return traineeService.findAllTrainee(classId, status, pageable);
    }

    @PutMapping("/{id}/trainee-disable")
    public void traineeDisable(@PathVariable("id") Long id, @RequestParam("userId") Long userId) {
        classUserService.disableUserInClass(id, userId);
    }
}
