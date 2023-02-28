package swp490.g7.OnlineLearningSystem.guest.service;

import swp490.g7.OnlineLearningSystem.entities.demo_test.domain.request.DemoTestDataRequest;
import swp490.g7.OnlineLearningSystem.entities.packages.domain.response.PackResponseDto;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.response.SubjectHeaderResponse;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.response.SubjectResponseDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.ResultTestDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.TestRandomResponseDto;

import java.util.List;

public interface GuestService {
    List<SubjectHeaderResponse> findAll();

    TestRandomResponseDto getDemoTest(Long id);

    ResultTestDto getResultTestDemo(Long id, TestRandomResponseDto request);

    SubjectResponseDto findById(Long id);

    List<PackResponseDto> getPackageBySubjectId(Long id);

    void saveDataDemoTest(DemoTestDataRequest request);
}
