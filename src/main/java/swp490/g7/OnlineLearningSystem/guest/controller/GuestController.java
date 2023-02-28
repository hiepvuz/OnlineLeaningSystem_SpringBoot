package swp490.g7.OnlineLearningSystem.guest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swp490.g7.OnlineLearningSystem.entities.demo_test.domain.request.DemoTestDataRequest;
import swp490.g7.OnlineLearningSystem.entities.packages.domain.response.PackResponseDto;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.response.SubjectHeaderResponse;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.response.SubjectResponseDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.ResultTestDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.TestRandomResponseDto;
import swp490.g7.OnlineLearningSystem.guest.service.GuestService;

import java.util.List;

@RestController
@RequestMapping("/guest")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @GetMapping("/subjects")
    public List<SubjectHeaderResponse> getSubjectByRole() {
        return guestService.findAll();
    }

    @GetMapping("/{id}/subject")
    public SubjectResponseDto getSubjectById(@PathVariable Long id) {
        return guestService.findById(id);
    }

    @GetMapping("/{id}/demo-test")
    public TestRandomResponseDto getDemoTest(@PathVariable Long id) {
        return guestService.getDemoTest(id);
    }

    @PostMapping("/{id}/demo-submit")
    public ResultTestDto submitSimulationAndDemo(@PathVariable Long id,
                                                 @RequestBody TestRandomResponseDto request) {
        return guestService.getResultTestDemo(id, request);
    }

    @GetMapping("/{id}/package")
    public List<PackResponseDto> getPackageBySubjectId(@PathVariable("id") Long id) {
        return guestService.getPackageBySubjectId(id);
    }

    @PostMapping("/demo-test-data-collect")
    public void saveDataDemoTest(@RequestBody DemoTestDataRequest request) {
        guestService.saveDataDemoTest(request);
    }
}
