package swp490.g7.OnlineLearningSystem.entities.subject.service.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.Classroom;
import swp490.g7.OnlineLearningSystem.entities.classroom.repository.ClassroomRepository;
import swp490.g7.OnlineLearningSystem.entities.setting.domain.Setting;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.Subject;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.request.SubjectRequestDto;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.response.SubjectHeaderResponse;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.response.SubjectResponseDto;
import swp490.g7.OnlineLearningSystem.entities.subject.repository.SubjectRepository;
import swp490.g7.OnlineLearningSystem.entities.subject.service.SubjectService;
import swp490.g7.OnlineLearningSystem.entities.user.domain.User;
import swp490.g7.OnlineLearningSystem.entities.user.repository.UserRepository;
import swp490.g7.OnlineLearningSystem.entities.user.services.impl.UserServiceImpl;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
import swp490.g7.OnlineLearningSystem.utilities.BeanUtility;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Override
    public PaginationResponse getAll(Pageable pageable) {
        PagedListHolder pagedListHolder =
                new PagedListHolder(subjectRepository.getAll());
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
    public SubjectResponseDto create(SubjectRequestDto request) {
        logger.info("Starting create subject!");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request must note be empty");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }
        boolean isCodeExisted = false;

        Set<String> subjectCodes = subjectRepository.getAllSubjectCode();
        for (String s : subjectCodes
        ) {
            if (request.getSubjectCode().equals(String.valueOf(s))) {
                isCodeExisted = true;
                break;
            }
        }
        if (isCodeExisted) {
            logger.error("Subject code is existed");
            throw new OnlineLearningException(ErrorTypes.SUBJECT_WITH_CODE_ALREADY_EXISTS);
        }

        Subject newSubject = BeanUtility.convertValue(request, Subject.class);
        subjectRepository.save(newSubject);
        logger.info("subject created success!");
        return BeanUtility.convertValue(newSubject, SubjectResponseDto.class);
    }

    @Override
    public SubjectResponseDto getById(Long id) {
        return subjectRepository.getSubjectById(id);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Subject> subject = subjectRepository.findById(id);
        if (ObjectUtils.isEmpty(subject)) {
            logger.error("Failed to get subject with id {}", id);
            throw new OnlineLearningException(ErrorTypes.SUBJECT_WITH_ID_NOT_FOUND);
        }
        subjectRepository.deleteById(id);
    }

    @Override
    public SubjectResponseDto update(Long subjectId, SubjectRequestDto request) {
        logger.info("Starting update subject!");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request is empty with user subjectId {}", subjectId);
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }
        //Check role for permission of current user
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> currentUser = userRepository.findByUsername(userDetails.getUsername());
        if (!currentUser.isPresent()) {
            logger.error("Failed to get current user");
            throw new OnlineLearningException(ErrorTypes.CURRENT_USER_NOT_FOUND);
        }
        Set<Object> roles = userRepository.getRole(currentUser.get().getUserId());
        if (!roles.contains(Setting.USER_ROLE_ADMIN) && !roles.contains(Setting.USER_ROLE_MANAGER)) {
            logger.error("Current user is not Admin or Manager!");
            throw new OnlineLearningException(ErrorTypes.ROLE_HAVE_NOT_PERMISSION);
        }

        // Check existing subject
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        if (!subject.isPresent()) {
            logger.error("Can not found subject with subjectId {}", subjectId);
            throw new OnlineLearningException(ErrorTypes.SUBJECT_WITH_ID_NOT_FOUND);
        }

        //Check subjectId manager and expert existing!
        if (!userRepository.existsById(request.getManagerId())) {
            logger.error("Can not found manager with subjectId {}", request.getManagerId());
            throw new OnlineLearningException(ErrorTypes.MANAGER_WITH_ID_NOT_EXIST);
        }
        if (!userRepository.existsById(request.getExpertId())) {
            logger.error("Can not found expert with subjectId {}", request.getExpertId());
            throw new OnlineLearningException(ErrorTypes.EXPERT_WITH_ID_NOT_EXIST);
        }

        Subject currentSubject = subject.get();

        if (roles.contains(Setting.USER_ROLE_ADMIN)) {
            currentSubject.setSubjectCode(request.getSubjectCode());
            currentSubject.setSubjectName(request.getSubjectName());
            currentSubject.setCategoryId(request.getCategoryId());
            currentSubject.setManagerId(request.getManagerId());
        }
        currentSubject.setExpertId(request.getExpertId());
        currentSubject.setStatus(request.getStatus());
        currentSubject.setBody(request.getBody());
        subjectRepository.save(currentSubject);
        logger.info("Update subject successfully");
        return getById(subjectId);
    }

    @Override
    public Set<Object> getAllManager() {
        return subjectRepository.getAllManager();
    }

    @Override
    public Set<Object> getAllExpert() {
        return subjectRepository.getAllExpert();
    }

    @Override
    public PaginationResponse filter(String subjectName, String subjectCode, Boolean status, Pageable pageable) {
        PagedListHolder pagedListHolder = new PagedListHolder(subjectRepository.filter(subjectName, subjectCode, status));
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
    public void enable(Long id) {
        logger.info("Starting enable classroom with id {}", id);
        Optional<Subject> classroom = subjectRepository.findById(id);
        if (!classroom.isPresent()) {
            logger.error("Can not found user with id {}", id);
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND);
        }
        classroom.get().setStatus(true);
        subjectRepository.save(classroom.get());
        logger.info("Successfully enable user with id {}", id);
    }

    @Override
    public void disable(Long id) {
        logger.info("Starting enable classroom with id {}", id);
        Optional<Subject> classroom = subjectRepository.findById(id);
        if (!classroom.isPresent()) {
            logger.error("Can not found user with id {}", id);
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND);
        }
        classroom.get().setStatus(false);
        subjectRepository.save(classroom.get());
        logger.info("Successfully enable user with id {}", id);
    }

    @Override
    public List<SubjectHeaderResponse> getSubjectByRole() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());

        if (!user.isPresent()) {
            logger.error("User not found with user name: {}", userDetails.getUsername());
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND, userDetails.getUsername());
        }
        Set<Setting> roles = user.get().getRoles();
        List<Subject> subjects;
        for (Setting s : roles) {
            switch (s.getSettingTitle()) {

                case Setting.USER_ROLE_MANAGER:
                    subjects = subjectRepository.findByManagerIdAndStatusIsTrue(user.get().getUserId());
                    return mapSubjectHeader(subjects);

                case Setting.USER_ROLE_EXPERT:
                    subjects = subjectRepository.findByExpertIdAndStatusIsTrue(user.get().getUserId());
                    return mapSubjectHeader(subjects);

                case Setting.USER_ROLE_TRAINER:
                    List<Classroom> classrooms = classroomRepository.findByTrainerId(user.get().getUserId());
                    Set<Long> subjectIds = classrooms.stream()
                            .map(Classroom::getSubjectId)
                            .collect(Collectors.toSet());
                    subjects = subjectRepository.findBySubjectIdInAndStatusIsTrue(new ArrayList<>(subjectIds));
                    return mapSubjectHeader(subjects);

                default:
                    subjects = subjectRepository.findAllByStatusIsTrue();
                    return mapSubjectHeader(subjects);
            }
        }
        return new ArrayList<>();
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

