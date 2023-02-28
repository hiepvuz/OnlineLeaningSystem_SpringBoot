package swp490.g7.OnlineLearningSystem.entities.packages.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swp490.g7.OnlineLearningSystem.entities.packages.domain.Pack;
import swp490.g7.OnlineLearningSystem.entities.packages.domain.dto.GradesClassDto;
import swp490.g7.OnlineLearningSystem.entities.packages.domain.dto.GradesDto;
import swp490.g7.OnlineLearningSystem.entities.packages.domain.dto.LearningProgressDto;
import swp490.g7.OnlineLearningSystem.entities.packages.domain.response.PackResponseDto;
import swp490.g7.OnlineLearningSystem.entities.packages.service.PackService;

import java.util.List;

@RestController
@RequestMapping("/package")
public class PackController {

    @Autowired
    private PackService packService;

    @GetMapping("/all")
    public List<Pack> findAll() {
        return packService.findAll();
    }

    @GetMapping("/learning-progress")
    public LearningProgressDto getLearningProgress(@RequestParam("subjectId") Long subjectId) {
        return packService.getLearningProgress(subjectId);
    }

    @GetMapping("/grades")
    public List<GradesDto> getGradeLessons(@RequestParam("subjectId") Long subjectId) {
        return packService.getGradeLessons(subjectId);
    }

    @GetMapping("/grades-class-lesson")
    public List<GradesClassDto> getGradeClassLessons(@RequestParam("subjectId") Long subjectId) {
        return packService.getGradeClassLessons(subjectId);
    }

    @GetMapping("/{id}/current")
    public List<PackResponseDto> getCurrentPackageByUser(@PathVariable("id") Long id) {
        return packService.getCurrentPackageByUser(id);
    }
}
