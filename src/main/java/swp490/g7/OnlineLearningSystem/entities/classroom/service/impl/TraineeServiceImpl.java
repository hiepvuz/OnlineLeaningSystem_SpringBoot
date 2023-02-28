package swp490.g7.OnlineLearningSystem.entities.classroom.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.Classroom;
import swp490.g7.OnlineLearningSystem.entities.classroom.repository.ClassroomRepository;
import swp490.g7.OnlineLearningSystem.entities.classroom.repository.TraineeRepository;
import swp490.g7.OnlineLearningSystem.entities.classroom.service.TraineeService;
import swp490.g7.OnlineLearningSystem.entities.user.domain.User;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.List;
import java.util.Optional;

@Service
public class TraineeServiceImpl implements TraineeService {

    private static final Logger logger = LogManager.getLogger(ClassroomServiceImpl.class);

    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Override
    public PaginationResponse findAllTrainee(Long classId, Boolean status, Pageable pageable) {
        Optional<Classroom> clazz = classroomRepository.findById(classId);
        if (!clazz.isPresent()) {
            logger.error("Class not found with class id: {}", classId);
            throw new OnlineLearningException(ErrorTypes.CLASS_NOT_FOUND, classId.toString());
        }
        PagedListHolder pagedListHolder = new PagedListHolder(traineeRepository.findAllTrainee(classId, status));
        pagedListHolder.setPage(pageable.getPageNumber());
        pagedListHolder.setPageSize(pageable.getPageSize());

        return PaginationResponse.builder()
                .total(pagedListHolder.getSource().size())
                .numberOfPage(pagedListHolder.getPageCount())
                .pageIndex(pageable.getPageNumber())
                .items(pagedListHolder.getPageList())
                .build();
    }

    @Override
    public List<User> findTraineeByEmailIn(List<String> emails) {
        return traineeRepository.findByEmailIn(emails);
    }

    @Override
    public Optional<User> findTraineeNotInClass(String email, Long classId) {
        return traineeRepository.findTraineeNotInClass(email, classId);
    }

}
