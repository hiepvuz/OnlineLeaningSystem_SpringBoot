package swp490.g7.OnlineLearningSystem.entities.classroom.service.impl;

import net.bytebuddy.utility.RandomString;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import swp490.g7.OnlineLearningSystem.auth.domain.payload.request.SignupRequest;
import swp490.g7.OnlineLearningSystem.auth.services.AuthService;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.ClassLesson;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.repository.ClassLessonRepository;
import swp490.g7.OnlineLearningSystem.entities.class_setting.domain.ClassSetting;
import swp490.g7.OnlineLearningSystem.entities.class_setting.repository.ClassSettingRepository;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.ClassUser;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.Classroom;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.request.ClassroomRequestDto;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.response.ClassroomResponseDto;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.response.TraineeResponse;
import swp490.g7.OnlineLearningSystem.entities.classroom.repository.ClassroomRepository;
import swp490.g7.OnlineLearningSystem.entities.classroom.service.ClassroomService;
import swp490.g7.OnlineLearningSystem.entities.classroom.service.TraineeService;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.Lesson;
import swp490.g7.OnlineLearningSystem.entities.lesson.repository.LessonRepository;
import swp490.g7.OnlineLearningSystem.entities.setting.domain.Setting;
import swp490.g7.OnlineLearningSystem.entities.setting.repository.SettingRepository;
import swp490.g7.OnlineLearningSystem.entities.subject.repository.SubjectRepository;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.domain.SubjectSetting;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.repository.SubjectSettingRepository;
import swp490.g7.OnlineLearningSystem.entities.user.domain.User;
import swp490.g7.OnlineLearningSystem.entities.user.domain.response.UserResponseDto;
import swp490.g7.OnlineLearningSystem.entities.user.services.UserService;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
import swp490.g7.OnlineLearningSystem.utilities.BeanUtility;
import swp490.g7.OnlineLearningSystem.utilities.DateLibs;
import swp490.g7.OnlineLearningSystem.utilities.ExcelHelper;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;
import swp490.g7.OnlineLearningSystem.verification.email.EmailService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClassroomServiceImpl implements ClassroomService {
    private static final Logger logger = LogManager.getLogger(ClassroomServiceImpl.class);

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private TraineeService traineeService;

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ClassLessonRepository classLessonRepository;

    @Autowired
    private ClassSettingRepository classSettingRepository;

    @Autowired
    private SubjectSettingRepository subjectSettingRepository;

    @Override
    public List<Classroom> findAll() {
        return classroomRepository.findAll();
    }

    @Override
    public PaginationResponse getAll(Pageable pageable) {
        PagedListHolder pagedListHolder =
                new PagedListHolder(classroomRepository.getAll());
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
    public ClassroomResponseDto findById(Long classId) {
        Optional<Classroom> clazz = classroomRepository.findById(classId);
        if (!clazz.isPresent()) {
            logger.error("Class not found with class id: {}", classId);
            throw new OnlineLearningException(ErrorTypes.CLASS_NOT_FOUND, classId.toString());
        }
        return BeanUtility.convertValue(clazz.get(), ClassroomResponseDto.class);
    }

    @Override
    public ClassroomResponseDto save(ClassroomRequestDto request) {
        logger.info("Start creating class...");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request must be not null or empty");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }
        boolean existSubject = subjectRepository.existsById(request.getSubjectId());
        if (!existSubject) {
            logger.error("Subject not found with subject id: {}", request.getSubjectId());
            throw new OnlineLearningException(ErrorTypes.SUBJECT_WITH_ID_NOT_FOUND, request.getSubjectId().toString());
        }
        UserResponseDto trainer = userService.findById(request.getTrainerId());
        if (ObjectUtils.isEmpty(trainer)) {
            logger.error("Trainee not exists with trainee id: {}", request.getTrainerId());
            throw new OnlineLearningException(ErrorTypes.TRAINEE_NOT_EXISTS);
        }
        Classroom classroom = new Classroom();
        classroom.setTrainerId(request.getTrainerId());
        classroom.setSupporterId(request.getSupporterId());
        classroom.setClassCode(request.getClassCode());
        classroom.setDescription(request.getDescription());
        classroom.setStatus(request.getStatus());
        classroom.setFromDate(DateLibs.convertDate(request.getFromDate()));
        classroom.setToDate(DateLibs.convertDate(request.getToDate()));
        classroom.setTerm(request.getTerm());
        classroom.setBranch(request.getBranch());
        classroom.setSubjectId(request.getSubjectId());
        ClassroomResponseDto classroomResponseDto = BeanUtility.convertValue(classroomRepository.save(classroom), ClassroomResponseDto.class);
        logger.info("Successfully create an class");
        classroomResponseDto.setTrainerUserName(trainer.getUsername());
        syncSubjectData(lessonRepository.findAllBySubjectId(request.getSubjectId()), classroom);
        return classroomResponseDto;
    }

    @Override
    public ClassroomResponseDto update(Long id, ClassroomRequestDto request) {
        logger.info("Start updating class...");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request must be not null or empty");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }
        Optional<Classroom> classroom = classroomRepository.findById(id);
        if (!classroom.isPresent()) {
            logger.error("Class not found with class id: {}", id);
            throw new OnlineLearningException(ErrorTypes.CLASS_NOT_FOUND, id.toString());
        }
        UserResponseDto trainer = userService.findById(request.getTrainerId());
        if (ObjectUtils.isEmpty(trainer)) {
            logger.error("Trainee not exists with trainee id: {}", request.getTrainerId());
            throw new OnlineLearningException(ErrorTypes.TRAINEE_NOT_EXISTS);
        }
        classroom.get().setTrainerId(request.getTrainerId());
        classroom.get().setSupporterId(request.getSupporterId());
        classroom.get().setClassCode(request.getClassCode());
        classroom.get().setDescription(request.getDescription());
        classroom.get().setStatus(request.getStatus());
        classroom.get().setFromDate(DateLibs.convertDate(request.getFromDate()));
        classroom.get().setToDate(DateLibs.convertDate(request.getToDate()));
        classroom.get().setBranch(request.getBranch());
        classroom.get().setTerm(request.getTerm());
        classroomRepository.save(classroom.get());
        logger.info("Successfully update an class");
        ClassroomResponseDto classroomResponseDto = BeanUtility.convertValue(classroom.get(), ClassroomResponseDto.class);
        classroomResponseDto.setTrainerUserName(trainer.getUsername());
        return classroomResponseDto;
    }

    @Override
    public PaginationResponse filter(Boolean status, String fromDate, String toDate, String classCode, String trainerUserName,
                                     String supporterUserName, String term, String branch, Long subjectId, Pageable pageable) {
        PagedListHolder pagedListHolder = new PagedListHolder(classroomRepository
                .filter(status, fromDate, toDate, classCode, trainerUserName, supporterUserName, term, branch,
                        subjectId, pageable));
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
    public ClassroomResponseDto getClassroomById(Long classId) {
        Boolean existClassroom = classroomRepository.existsById(classId);
        if (!existClassroom) {
            logger.error("Class not exists with class id: {}", classId);
            throw new OnlineLearningException(ErrorTypes.CLASS_NOT_FOUND, classId.toString());
        }
        return classroomRepository.getClassroomById(classId);
    }

    @Override
    public void enable(Long id) {
        logger.info("Starting enable classroom with id {}", id);
        Optional<Classroom> classroom = classroomRepository.findById(id);
        if (!classroom.isPresent()) {
            logger.error("Can not found user with id {}", id);
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND);
        }
        classroom.get().setStatus(true);
        classroomRepository.save(classroom.get());
        logger.info("Successfully enable user with id {}", id);
    }

    @Override
    public void disable(Long id) {
        logger.info("Starting enable classroom with id {}", id);
        Optional<Classroom> classroom = classroomRepository.findById(id);
        if (!classroom.isPresent()) {
            logger.error("Can not found user with id {}", id);
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND);
        }
        classroom.get().setStatus(false);
        classroomRepository.save(classroom.get());
        logger.info("Successfully enable user with id {}", id);
    }

    @Override
    public TraineeResponse uploadFile(Long classId, MultipartFile file) {
        try {
            List<User> userExcel = ExcelHelper.excelToTutorials(file.getInputStream());
            List<User> userNotExists = ExcelHelper.excelToTutorials(file.getInputStream());
            List<String> emails = new ArrayList<>();
            Set<String> checkUniques = userService.getEmails();
            for (User u : userExcel
            ) {
                if (u.getEmail() != null) {
                    emails.add(u.getEmail());
                }
            }
            for (String e : emails) {
                if (!isValidEmail(e)) {
                    logger.error("Email is wrong format", e);
                    throw new OnlineLearningException(ErrorTypes.EMAIL_IS_WRONG_FORMAT);
                }
                if (!checkUniques.add(e)) {
                    logger.error("Email is duplicate", e);
                    throw new OnlineLearningException(ErrorTypes.EMAIL_IS_DUPLICATE);
                }
            }
            List<User> userExists = userService.findByEmailIn(emails);
            for (User u : userExists
            ) {
                for (int i = 0; i < userNotExists.size(); i++) {
                    if (u.getEmail().equals(userNotExists.get(i).getEmail())) {
                        userNotExists.remove(i);
                    }
                }
            }
            List<User> traineeNotInClasses;
            List<String> emailUserNotExists = new ArrayList<>();
            int count = 0;
            for (User u : userNotExists
            ) {
                if (u.getEmail() != null) {
                    SignupRequest request = new SignupRequest();
                    request.setFullName(u.getFullName());
                    request.setEmail(u.getEmail());
                    String password = RandomString.make(10);
                    request.setPassword(password);
                    authService.adminRegisterUser(request);
                    emailUserNotExists.add(u.getEmail());
                    count++;
                }
            }
            traineeNotInClasses = traineeService.findTraineeByEmailIn(emailUserNotExists);
            emailService.sendEmails(traineeNotInClasses);
            for (User u : userExists
            ) {
                Optional<User> traineeClass = traineeService.findTraineeNotInClass(u.getEmail(), classId);
                if (traineeClass.isPresent()) {
                    traineeNotInClasses.add(u);
                }
            }
            Optional<Classroom> classroom = classroomRepository.findById(classId);
            List<ClassUser> classUsers = new ArrayList<>();
            Set<Setting> settings = new HashSet<>();
            settings.add(settingRepository.findBySettingTitle(Setting.USER_ROLE_TRAINEE));
            for (User u : traineeNotInClasses
            ) {
                u.setRoles(settings);
                ClassUser classUser = new ClassUser();
                classUser.setClassroom(classroom.get());
                classUser.setUser(u);
                classUser.setStatus(Boolean.TRUE);
                classUsers.add(classUser);
            }
            classroom.get().getClassUsers().addAll(classUsers);
            classroomRepository.save(classroom.get());

            TraineeResponse traineeResponse = new TraineeResponse();
            traineeResponse.setTotalUserAdded(traineeNotInClasses.size());
            traineeResponse.setTotalUserRegistered(count);
            return traineeResponse;
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Async
    @Override
    public void syncSubjectData(List<Lesson> lessons, Classroom classroom) {
        if (ObjectUtils.isEmpty(classroom)) {
            logger.info("Class not found, data is not sync");
            return;
        }
        if (CollectionUtils.isNotEmpty(lessons)) {
            logger.info("Starting sync class lesson data with class id {}", classroom.getClassId());
            List<Lesson> normalLesson = lessons.stream()
                    .filter(l -> ObjectUtils.isEmpty(l.getTestId()) && BooleanUtils.isTrue(l.getStatus()))
                    .collect(Collectors.toList());
            List<Long> moduleIds = normalLesson.stream()
                    .map(Lesson::getModuleId)
                    .collect(Collectors.toList());

            List<SubjectSetting> subjectSettings = subjectSettingRepository.findBySubjectSettingIdIn(moduleIds);

            Map<Long, Long> syncModule = new HashMap<>();
            List<ClassSetting> classSettings = new ArrayList<>();
            subjectSettings.forEach(s -> {
                ClassSetting classSetting = new ClassSetting();
                classSetting.setClassId(classroom.getClassId());
                classSetting.setClassSettingTitle(s.getSubjectSettingTitle());
                classSetting.setDisplayOrder(s.getDisplayOrder());
                classSetting.setSettingValue(s.getSettingValue());
                classSetting.setTypeId(ClassSetting.CLASS_MODULE);
                classSetting.setDescription(s.getDescription());
                classSetting.setStatus(s.getStatus());
                classSettings.add(classSetting);
                classSettingRepository.save(classSetting);
                syncModule.put(s.getSubjectSettingId(), classSetting.getClassSettingId());
            });

            List<ClassLesson> classLessons = BeanUtility.mapList(normalLesson, ClassLesson.class);
            classLessons.forEach(cl -> {
                cl.setClassroom(classroom);
                syncModule.forEach((k, v) -> {
                    if (k.equals(cl.getModuleId())) {
                        cl.setModuleId(v);
                    }
                });
            });
            classLessonRepository.saveAll(classLessons);
        }
    }

    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        return email.matches(regex);
    }
}
