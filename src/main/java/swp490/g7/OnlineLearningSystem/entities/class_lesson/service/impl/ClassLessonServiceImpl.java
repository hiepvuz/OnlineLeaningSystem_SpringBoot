package swp490.g7.OnlineLearningSystem.entities.class_lesson.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.ClassLesson;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.dto.ClassDto;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.dto.ClassLessonDto;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.dto.ClassLessonFilterDto;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.dto.ClassLessonModuleDto;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.request.ClassLessonRequestDto;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.response.ClassLessonResponseDto;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.repository.ClassLessonRepository;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.service.ClassLessonService;
import swp490.g7.OnlineLearningSystem.entities.class_setting.repository.ClassSettingRepository;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.Classroom;
import swp490.g7.OnlineLearningSystem.entities.classroom.repository.ClassroomRepository;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.Lesson;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.dto.SubjectLessonDto;
import swp490.g7.OnlineLearningSystem.entities.lesson.repository.LessonRepository;
import swp490.g7.OnlineLearningSystem.entities.setting.domain.Setting;
import swp490.g7.OnlineLearningSystem.entities.study_result.domain.StudyResult;
import swp490.g7.OnlineLearningSystem.entities.study_result.repository.StudyResultRepository;
import swp490.g7.OnlineLearningSystem.entities.study_result.service.StudyResultService;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.Subject;
import swp490.g7.OnlineLearningSystem.entities.subject.repository.SubjectRepository;
import swp490.g7.OnlineLearningSystem.entities.test.domain.Test;
import swp490.g7.OnlineLearningSystem.entities.test.repository.TestRepository;
import swp490.g7.OnlineLearningSystem.entities.test.service.TestService;
import swp490.g7.OnlineLearningSystem.entities.user.repository.UserRepository;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
import swp490.g7.OnlineLearningSystem.utilities.BeanUtility;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClassLessonServiceImpl implements ClassLessonService {
    private static final Logger logger = LogManager.getLogger(ClassLessonServiceImpl.class);

    @Autowired
    private ClassLessonRepository classLessonRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private ClassSettingRepository classSettingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyResultRepository studyResultRepository;

    @Autowired
    private StudyResultService studyResultService;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private TestService testService;

    @Override
    public ClassLessonResponseDto findById(Long classLessonId) {
        Optional<ClassLesson> classLesson = classLessonRepository.findById(classLessonId);
        if (!classLesson.isPresent()) {
            logger.error("Class less not found with class lesson id: {}", classLessonId);
            throw new OnlineLearningException(ErrorTypes.CLASS_LESSON_NOT_FOUND, classLessonId.toString());
        }
        return mappingClassLesson(classLesson.get());
    }

    @Override
    public ClassLessonResponseDto create(ClassLessonRequestDto request) {
        logger.info("Starting create a class lesson ...");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request invalid");
            throw new OnlineLearningException(ErrorTypes.REQUEST_MUST_NOT_BE_EMPTY);
        }
        Optional<Subject> existSubject = subjectRepository.findById(request.getSubjectId());
        if (!existSubject.isPresent()) {
            logger.error("Subject not found with subject id: {}", request.getSubjectId());
            throw new OnlineLearningException(ErrorTypes.SUBJECT_WITH_ID_NOT_FOUND, request.getSubjectId().toString());
        }
        Optional<Classroom> existClassroom = classroomRepository.findById(request.getClassId());
        if (!existClassroom.isPresent()) {
            logger.error("Class not found with class id: {}", request.getClassId());
            throw new OnlineLearningException(ErrorTypes.CLASS_NOT_FOUND, request.getClassId().toString());
        }
        ClassLesson classLesson = new ClassLesson();
        classLesson.setName(request.getName());
        classLesson.setSubject(existSubject.get());
        classLesson.setStatus(request.getStatus());
        classLesson.setModuleId(request.getModuleId());
        classLesson.setTypeLesson(request.getTypeLesson());
        classLesson.setClassroom(existClassroom.get());
        classLesson.setDisplayOrder(request.getDisplayOrder());
        classLesson.setUpdatedOrder(new Date());
        classLesson.setDuration(request.getDuration());
        if (ObjectUtils.isEmpty(request.getTypeLesson())) {
            Optional<Lesson> lesson = lessonRepository.findById(request.getLessonId());
            if (!lesson.isPresent()) {
                logger.error("Lesson not found with lesson id {}", request.getLessonId());
                throw new OnlineLearningException(ErrorTypes.LESSON_NOT_FOUND, String.valueOf(request.getLessonId()));
            }
            classLesson.setLessonText(lesson.get().getLessonText());
            classLesson.setVideoUrl(lesson.get().getVideoUrl());
            classLesson.setAttachmentUrl(lesson.get().getAttachmentUrl());
            classLesson.setTypeLesson(ClassLesson.CLASS_LESSON_TYPE_NORMAL);
        } else {
            if (request.getTypeLesson().equalsIgnoreCase(ClassLesson.CLASS_LESSON_TYPE_NORMAL)) {
                classLesson.setLessonText(request.getLessonText());
                classLesson.setVideoUrl(request.getVideoUrl());
            } else {
                classLesson.setTestId(request.getTestId());
            }
        }
        return mappingClassLesson(classLessonRepository.save(classLesson));
    }

    @Override
    public ClassLessonResponseDto update(Long classLessonId, ClassLessonRequestDto request) {
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request invalid");
            throw new OnlineLearningException(ErrorTypes.REQUEST_MUST_NOT_BE_EMPTY);
        }
        Optional<Classroom> existClassroom = classroomRepository.findById(request.getClassId());
        if (!existClassroom.isPresent()) {
            logger.error("Class not found with class id: {}", request.getClassId());
            throw new OnlineLearningException(ErrorTypes.CLASS_NOT_FOUND, request.getClassId().toString());
        }
        Optional<ClassLesson> classLesson = classLessonRepository.findById(classLessonId);
        if (!classLesson.isPresent()) {
            logger.error("Class Lesson not found with class lesson id: {}", classLessonId);
            throw new OnlineLearningException(ErrorTypes.CLASS_LESSON_NOT_FOUND, classLessonId.toString());
        }
        classLesson.get().setName(request.getName());
        classLesson.get().setStatus(request.getStatus());
        classLesson.get().setModuleId(request.getModuleId());
        classLesson.get().setClassroom(existClassroom.get());
        classLesson.get().setDuration(request.getDuration());
        if (!Objects.equals(classLesson.get().getDisplayOrder(), request.getDisplayOrder())) {
            classLesson.get().setDisplayOrder(request.getDisplayOrder());
            classLesson.get().setUpdatedOrder(new Date());
        }
        if (!Objects.equals(request.getSubjectId(), classLesson.get().getSubject().getSubjectId())) {
            Optional<Subject> existSubject = subjectRepository.findById(request.getSubjectId());
            if (!existSubject.isPresent()) {
                logger.error("Subject not found with subject id: {}", request.getSubjectId());
                throw new OnlineLearningException(ErrorTypes.SUBJECT_WITH_ID_NOT_FOUND, request.getSubjectId().toString());
            }
            classLesson.get().setSubject(existSubject.get());
        }
        if (ObjectUtils.isEmpty(request.getTypeLesson())) {
            Optional<Lesson> lesson = lessonRepository.findById(request.getLessonId());
            if (!lesson.isPresent()) {
                logger.error("Lesson not found with lesson id {}", request.getLessonId());
                throw new OnlineLearningException(ErrorTypes.LESSON_NOT_FOUND, String.valueOf(request.getLessonId()));
            }
            classLesson.get().setLessonText(lesson.get().getLessonText());
            classLesson.get().setVideoUrl(lesson.get().getVideoUrl());
            classLesson.get().setAttachmentUrl(lesson.get().getAttachmentUrl());
        } else {
            if (request.getTypeLesson().equalsIgnoreCase(ClassLesson.CLASS_LESSON_TYPE_NORMAL)) {
                classLesson.get().setLessonText(request.getLessonText());
                classLesson.get().setVideoUrl(request.getVideoUrl());
                classLesson.get().setAttachmentUrl(request.getAttachmentUrl());
            } else {
                classLesson.get().setTestId(request.getTestId());
            }
        }
        return mappingClassLesson(classLessonRepository.save(classLesson.get()));
    }

    @Override
    public PaginationResponse findAll(Long subjectId, Long classId, Long moduleId, Boolean status, String typeLesson,
                                      String name, Pageable pageable) {
        PagedListHolder pagedListHolder =
                new PagedListHolder(classLessonRepository.findAll(subjectId, classId, moduleId, status, typeLesson, name));
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
    public PaginationResponse getAllByClassModule(Long subjectId, Pageable pageable) {
        return null;
    }

    @Override
    public void delete(Long classLessonId) {
        Optional<ClassLesson> classLesson = classLessonRepository.findById(classLessonId);
        if (!classLesson.isPresent()) {
            logger.error("Class less not found with class lesson id: {}", classLessonId);
            throw new OnlineLearningException(ErrorTypes.CLASS_LESSON_NOT_FOUND, classLessonId.toString());
        }
        classLessonRepository.delete(classLesson.get());
    }

    @Override
    public List<ClassLessonModuleDto> filter(Long subjectId, Long classId, Long moduleId, Boolean status,
                                             String typeLesson, String name) {
        List<ClassLessonFilterDto> classLessons = classLessonRepository.findAll(subjectId, classId, moduleId, status,
                typeLesson, name);
        Set<Long> moduleIds = classLessons.stream()
                .map(ClassLessonFilterDto::getModuleId)
                .collect(Collectors.toSet());
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userRepository.getIdByUserName(userDetails.getUsername());

        if (ObjectUtils.isEmpty(userId)) {
            logger.error("User not found with user name: {}", userDetails.getUsername());
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND, userDetails.getUsername());
        }
        List<ClassLessonModuleDto> classLessonModuleResponse = new ArrayList<>();
        moduleIds.forEach(m -> {
            ClassLessonModuleDto classLessonModuleDto = new ClassLessonModuleDto();
            List<ClassLessonFilterDto> classLessonFilters = classLessons.stream()
                    .filter(c -> Objects.equals(c.getModuleId(), m))
                    .collect(Collectors.toList());
            List<ClassLessonDto> classLessonResponse = BeanUtility.mapList(classLessonFilters, ClassLessonDto.class);
            List<ClassLessonDto> sortedClassLessons = classLessonResponse.stream()
                    .sorted(classLessonComparator())
                    .collect(Collectors.toList());
            Set<Object> roleUser = userRepository.getRole(userId);
            if (CollectionUtils.isNotEmpty(roleUser)) {
                if (roleUser.contains(Setting.USER_ROLE_TRAINEE)) {
                    sortedClassLessons.forEach(l -> {
                        Optional<StudyResult> studyResult = studyResultRepository.findBySubjectIdAndClassLessonIdAndUserId(
                                l.getSubjectId(), l.getClassLessonId(), userId);
                        if (studyResult.isPresent()) {
                            if (ObjectUtils.isNotEmpty(studyResult.get().getIsCompleted()) && studyResult.get().getIsCompleted()) {
                                l.setIsCompleted(Boolean.TRUE);
                            }
                            if (ObjectUtils.isNotEmpty(studyResult.get().getIsPassed()) && studyResult.get().getIsPassed()) {
                                l.setIsCompleted(Boolean.TRUE);
                            }
                        }
                    });
                }
            }
            classLessonModuleDto.setModuleId(sortedClassLessons.get(0).getModuleId());
            classLessonModuleDto.setModuleName(sortedClassLessons.get(0).getClassSettingTitle());
            classLessonModuleDto.setStatus(classSettingRepository.getStatusByClassSettingId(
                    sortedClassLessons.get(0).getModuleId()
            ));
            classLessonModuleDto.setClassLessons(sortedClassLessons);
            classLessonModuleResponse.add(classLessonModuleDto);
        });
        return classLessonModuleResponse;
    }

    @Override
    public void enable(Long classLessonId) {
        logger.info("Starting enable class lesson by id: {}", classLessonId);
        Optional<ClassLesson> classLesson = classLessonRepository.findById(classLessonId);
        if (!classLesson.isPresent()) {
            logger.error("Class less not found with class lesson id: {}", classLessonId);
            throw new OnlineLearningException(ErrorTypes.CLASS_LESSON_NOT_FOUND, classLessonId.toString());
        }
        classLesson.get().setStatus(Boolean.TRUE);
        classLessonRepository.save(classLesson.get());
    }

    @Override
    public void disable(Long classLessonId) {
        logger.info("Starting disable class lesson by id: {}", classLessonId);
        Optional<ClassLesson> classLesson = classLessonRepository.findById(classLessonId);
        if (!classLesson.isPresent()) {
            logger.error("Class less not found with class lesson id: {}", classLessonId);
            throw new OnlineLearningException(ErrorTypes.CLASS_LESSON_NOT_FOUND, classLessonId.toString());
        }
        classLesson.get().setStatus(Boolean.FALSE);
        classLessonRepository.save(classLesson.get());
    }

    @Override
    public void submit(Long id) {
        logger.info("Submit lesson data with lesson id: {}", id);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userRepository.getIdByUserName(userDetails.getUsername());

        if (ObjectUtils.isEmpty(userId)) {
            logger.error("User not found with user name: {}", userDetails.getUsername());
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND, userDetails.getUsername());
        }
        logger.info("Get user id successfully!");
        Optional<ClassLesson> existClassLesson = classLessonRepository.findById(id);
        if (!existClassLesson.isPresent()) {
            logger.error("Lesson not found with lesson id: {}", id);
            throw new OnlineLearningException(ErrorTypes.LESSON_NOT_FOUND, id.toString());
        }
        logger.info("Get lesson id successfully!");
        Optional<StudyResult> existStudyResult = studyResultRepository.findBySubjectIdAndClassLessonIdAndUserId(
                existClassLesson.get().getSubject().getSubjectId(), id, userId
        );
        logger.info("Get lesson id successfully!");
        if (!existStudyResult.isPresent()) {
            ClassLesson classLesson = existClassLesson.get();
            StudyResult studyResult = new StudyResult();
            studyResult.setUserId(userId);
            studyResult.setIsCompleted(Boolean.TRUE);
            studyResult.setSubjectId(classLesson.getSubject().getSubjectId());
            studyResult.setClassLessonId(classLesson.getClassLessonId());
            logger.info("Start submit data classLesson!");
            studyResultService.submitDataLesson(studyResult);
        } else {
            existStudyResult.get().setIsCompleted(Boolean.TRUE);
            studyResultService.submitDataLesson(existStudyResult.get());
        }
    }

    private ClassLessonResponseDto mappingClassLesson(ClassLesson classLesson) {
        ClassLessonResponseDto classLessonResponseDto = new ClassLessonResponseDto();
        classLessonResponseDto.setClassLessonId(classLesson.getClassLessonId());
        classLessonResponseDto.setModuleId(classLesson.getModuleId());
        classLessonResponseDto.setStatus(classLesson.getStatus());
        classLessonResponseDto.setName(classLesson.getName());
        classLessonResponseDto.setTypeLesson(classLesson.getTypeLesson());
        classLessonResponseDto.setVideoUrl(classLesson.getVideoUrl());
        classLessonResponseDto.setLessonText(classLesson.getLessonText());
        classLessonResponseDto.setDisplayOrder(classLesson.getDisplayOrder());
        classLessonResponseDto.setLessonText(classLesson.getLessonText());
        classLessonResponseDto.setAttachmentUrl(classLesson.getAttachmentUrl());
        classLessonResponseDto.setDuration(classLesson.getDuration());
        if (ObjectUtils.isNotEmpty(classLesson.getTestId())) {
            classLessonResponseDto.setTestId(classLesson.getTestId());
        }

        SubjectLessonDto subjectLessonDto = new SubjectLessonDto();
        subjectLessonDto.setSubjectId(classLesson.getSubject().getSubjectId());
        subjectLessonDto.setSubjectCode(classLesson.getSubject().getSubjectCode());
        subjectLessonDto.setSubjectName(classLesson.getSubject().getSubjectName());
        subjectLessonDto.setBody(classLesson.getSubject().getBody());
        subjectLessonDto.setStatus(classLesson.getSubject().getStatus());
        subjectLessonDto.setCategoryId(classLesson.getSubject().getCategoryId());

        ClassDto classDto = new ClassDto();
        classDto.setClassId(classLesson.getClassroom().getClassId());
        classDto.setClassCode(classLesson.getClassroom().getClassCode());
        classDto.setLessonText(classLesson.getClassroom().getDescription());
        classDto.setStatus(classLesson.getClassroom().getStatus());

        classLessonResponseDto.setSubject(subjectLessonDto);
        classLessonResponseDto.setClassDto(classDto);
        if (classLesson.getTypeLesson().equalsIgnoreCase(ClassLesson.CLASS_LESSON_TYPE_QUIZ)) {
            if (ObjectUtils.isEmpty(classLesson.getTestId())) {
                logger.error("Quiz lesson not assigned any test!");
                throw new OnlineLearningException(ErrorTypes.QUIZ_LESSON_NOT_ASSIGNED_ANY_TEST,
                        classLesson.getClassLessonId().toString());
            }
            Optional<Test> test = testRepository.findById(classLesson.getTestId());
            if (test.isPresent()) {
                classLessonResponseDto.setTestId(classLesson.getTestId());
                classLessonResponseDto.setTestType(test.get().getTestType());
                classLessonResponseDto.setTestTitle(test.get().getName());
                classLessonResponseDto.setPassRate(test.get().getPassRate());
                classLessonResponseDto.setNumberOfQuestion(testService.countTotalQuestionInTestQuizLessonAndPracticeTest(test.get()));
            }
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userRepository.getIdByUserName(userDetails.getUsername());

        if (ObjectUtils.isEmpty(userId)) {
            logger.error("User not found with user name: {}", userDetails.getUsername());
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND, userDetails.getUsername());
        }
        Optional<StudyResult> studyResult = studyResultRepository.findBySubjectIdAndClassLessonIdAndUserId(
                classLesson.getSubject().getSubjectId(), classLesson.getClassLessonId(), userId);
        if (studyResult.isPresent()) {
            if (ObjectUtils.isNotEmpty(studyResult.get().getIsCompleted()) && studyResult.get().getIsCompleted()) {
                classLessonResponseDto.setIsCompleted(Boolean.TRUE);
            }
            if (ObjectUtils.isNotEmpty(studyResult.get().getIsPassed()) && studyResult.get().getIsPassed()) {
                classLessonResponseDto.setIsCompleted(Boolean.TRUE);
            }
        }
        return classLessonResponseDto;
    }

    private Comparator<ClassLessonDto> classLessonComparator() {
        return Comparator
                .comparing(ClassLessonDto::getDisplayOrder)
                .thenComparing(ClassLessonDto::getUpdatedOrder, Comparator.nullsFirst(Comparator.naturalOrder()));
    }
}
