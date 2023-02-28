package swp490.g7.OnlineLearningSystem.entities.demo_test.service.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp490.g7.OnlineLearningSystem.entities.demo_test.domain.DemoTest;
import swp490.g7.OnlineLearningSystem.entities.demo_test.domain.request.DemoTestDataRequest;
import swp490.g7.OnlineLearningSystem.entities.demo_test.repository.DemoTestDataRepository;
import swp490.g7.OnlineLearningSystem.entities.demo_test.service.DemoTestDataService;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.Subject;
import swp490.g7.OnlineLearningSystem.entities.subject.repository.SubjectRepository;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
import swp490.g7.OnlineLearningSystem.utilities.BeanUtility;

import java.util.Optional;

@Service
public class DemoTestDataServiceImpl implements DemoTestDataService {
    private static final Logger logger = LogManager.getLogger(DemoTestDataServiceImpl.class);

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private DemoTestDataRepository demoTestDataRepository;

    @Override
    public void save(DemoTestDataRequest request) {
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request invalid");
            throw new OnlineLearningException(ErrorTypes.REQUEST_MUST_NOT_BE_EMPTY);
        }
        Optional<Subject> existSubject = subjectRepository.findById(request.getSubjectId());
        if (!existSubject.isPresent()) {
            logger.error("Subject not found with subject id: {}", request.getSubjectId());
            throw new OnlineLearningException(ErrorTypes.SUBJECT_WITH_ID_NOT_FOUND, request.getSubjectId().toString());
        }
        demoTestDataRepository.save(BeanUtility.convertValue(request, DemoTest.class));
    }
}
