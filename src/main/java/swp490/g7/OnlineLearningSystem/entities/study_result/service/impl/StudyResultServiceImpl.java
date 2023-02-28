package swp490.g7.OnlineLearningSystem.entities.study_result.service.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp490.g7.OnlineLearningSystem.entities.study_result.domain.StudyResult;
import swp490.g7.OnlineLearningSystem.entities.study_result.repository.StudyResultRepository;
import swp490.g7.OnlineLearningSystem.entities.study_result.service.StudyResultService;
import swp490.g7.OnlineLearningSystem.errorhandling.CommonErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;

@Service
public class StudyResultServiceImpl implements StudyResultService {
    private static final Logger logger = LogManager.getLogger(StudyResultServiceImpl.class);

    @Autowired
    private StudyResultRepository studyResultRepository;

    @Override
    public void submitDataLesson(StudyResult studyResult) {
        logger.info("Submit data lesson result");
        if (ObjectUtils.isEmpty(studyResult)) {
            logger.error("Result is empty");
            throw new OnlineLearningException(CommonErrorTypes.INVALID_INPUT);
        }
        studyResultRepository.save(studyResult);
    }
}
