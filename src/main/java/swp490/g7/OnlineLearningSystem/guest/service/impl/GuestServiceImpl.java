package swp490.g7.OnlineLearningSystem.guest.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp490.g7.OnlineLearningSystem.entities.demo_test.domain.request.DemoTestDataRequest;
import swp490.g7.OnlineLearningSystem.entities.demo_test.service.DemoTestDataService;
import swp490.g7.OnlineLearningSystem.entities.packages.domain.Pack;
import swp490.g7.OnlineLearningSystem.entities.packages.domain.response.PackResponseDto;
import swp490.g7.OnlineLearningSystem.entities.packages.repository.PackRepository;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.Subject;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.response.SubjectHeaderResponse;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.response.SubjectResponseDto;
import swp490.g7.OnlineLearningSystem.entities.subject.repository.SubjectRepository;
import swp490.g7.OnlineLearningSystem.entities.subject.service.SubjectService;
import swp490.g7.OnlineLearningSystem.entities.test.domain.Test;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.ResultTestDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.TestRandomResponseDto;
import swp490.g7.OnlineLearningSystem.entities.test.repository.TestRepository;
import swp490.g7.OnlineLearningSystem.entities.test.service.TestService;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
import swp490.g7.OnlineLearningSystem.guest.service.GuestService;
import swp490.g7.OnlineLearningSystem.utilities.BeanUtility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GuestServiceImpl implements GuestService {

    private static final Logger logger = LogManager.getLogger(GuestServiceImpl.class);

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TestService testService;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private PackRepository packRepository;

    @Autowired
    private DemoTestDataService demoTestDataService;

    @Override
    public List<SubjectHeaderResponse> findAll() {
        List<Subject> subjects = subjectRepository.findAllByStatusIsTrue();
        return mapSubjectHeader(subjects);
    }

    @Override
    public TestRandomResponseDto getDemoTest(Long id) {
        Optional<Subject> existSubject = subjectRepository.findById(id);
        if (!existSubject.isPresent()) {
            logger.error("Subject not found with subject id: {}", id);
            throw new OnlineLearningException(ErrorTypes.SUBJECT_WITH_ID_NOT_FOUND, id.toString());
        }
        List<Test> testDemo = testRepository.findBySubjectAndTestType(existSubject.get(), Test.DEMO_TEST);
        if (CollectionUtils.isEmpty(testDemo)) {
            logger.info("Test demo of subject id {} is empty", id);
            throw new OnlineLearningException(ErrorTypes.TEST_DEMO_IS_EMPTY, id.toString());
        }
        Collections.shuffle(testDemo);
        Long testId = testDemo.get(0).getTestId();
        return testService.getSimulationAndDemo(testId);
    }

    @Override
    public ResultTestDto getResultTestDemo(Long id, TestRandomResponseDto request) {
        return testService.getAnswerSimulationAndDemo(id, request);
    }

    @Override
    public SubjectResponseDto findById(Long id) {
        return subjectService.getById(id);
    }

    @Override
    public List<PackResponseDto> getPackageBySubjectId(Long id) {
        Optional<Subject> existSubject = subjectRepository.findById(id);
        if (!existSubject.isPresent()) {
            logger.error("Subject not found with subject id: {}", id);
            throw new OnlineLearningException(ErrorTypes.SUBJECT_WITH_ID_NOT_FOUND, id.toString());
        }
        List<Pack> packs = packRepository.findAllBySubjectId(id);
        List<PackResponseDto> packResponses = BeanUtility.mapList(packs, PackResponseDto.class);
        return packResponses;
    }

    @Override
    public void saveDataDemoTest(DemoTestDataRequest request) {
        demoTestDataService.save(request);
    }

    private List<SubjectHeaderResponse> mapSubjectHeader(List<Subject> subjects) {
        List<SubjectHeaderResponse> subjectHeaderResponses = new ArrayList<>();
        subjects.forEach(s -> {
            SubjectHeaderResponse subjectHeaderResponse = new SubjectHeaderResponse();
            subjectHeaderResponse.setSubjectId(s.getSubjectId());
            subjectHeaderResponse.setSubjectName(s.getSubjectName());
            subjectHeaderResponse.setSubjectCode(s.getSubjectCode());
            subjectHeaderResponses.add(subjectHeaderResponse);
        });
        return subjectHeaderResponses;
    }
}
