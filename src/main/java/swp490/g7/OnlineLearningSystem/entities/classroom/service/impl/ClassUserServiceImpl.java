package swp490.g7.OnlineLearningSystem.entities.classroom.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.ClassUser;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.ClassUserId;
import swp490.g7.OnlineLearningSystem.entities.classroom.repository.ClassUserRepository;
import swp490.g7.OnlineLearningSystem.entities.classroom.service.ClassUserService;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;

import java.util.Optional;

@Service
public class ClassUserServiceImpl implements ClassUserService {
    private static final Logger logger = LogManager.getLogger(ClassUserServiceImpl.class);

    @Autowired
    private ClassUserRepository classUserRepository;

    @Override
    public void disableUserInClass(Long classId, Long userId) {
        logger.info("Change status trainee {} in class {}", userId, classId);
        ClassUserId classUserId = new ClassUserId(classId, userId);
        Optional<ClassUser> existClassUser = classUserRepository.findById(classUserId);
        if (!existClassUser.isPresent()) {
            logger.error("User not found with user id {} in class id {}", userId, classId);
            throw new OnlineLearningException(ErrorTypes.CLASS_USER_NOT_FOUND, userId.toString(), classId.toString());
        }
        existClassUser.get().setStatus(Boolean.FALSE);
        classUserRepository.save(existClassUser.get());
    }
}
