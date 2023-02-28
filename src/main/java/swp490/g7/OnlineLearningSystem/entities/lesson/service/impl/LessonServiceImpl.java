package swp490.g7.OnlineLearningSystem.entities.lesson.service.impl;

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
import swp490.g7.OnlineLearningSystem.entities.class_lesson.repository.ClassLessonRepository;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.Lesson;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.dto.LessonConfigTestDto;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.dto.LessonDto;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.dto.LessonFilterDto;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.dto.LessonModuleDto;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.dto.SubjectLessonDto;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.request.LessonRequestDto;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.response.LessonResponseDto;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.response.NormalLessonResponse;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.response.QuizLessonResponse;
import swp490.g7.OnlineLearningSystem.entities.lesson.repository.LessonRepository;
import swp490.g7.OnlineLearningSystem.entities.lesson.service.LessonService;
import swp490.g7.OnlineLearningSystem.entities.question.repository.QuestionRepository;
import swp490.g7.OnlineLearningSystem.entities.setting.domain.Setting;
import swp490.g7.OnlineLearningSystem.entities.study_result.domain.StudyResult;
import swp490.g7.OnlineLearningSystem.entities.study_result.repository.StudyResultRepository;
import swp490.g7.OnlineLearningSystem.entities.study_result.service.StudyResultService;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.Subject;
import swp490.g7.OnlineLearningSystem.entities.subject.repository.SubjectRepository;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.domain.SubjectSetting;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.repository.SubjectSettingRepository;
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
public class LessonServiceImpl implements LessonService {
    private static final Logger logger = LogManager.getLogger(LessonServiceImpl.class);

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TestService testService;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyResultService studyResultService;

    @Autowired
    private StudyResultRepository studyResultRepository;

    @Autowired
    private SubjectSettingRepository subjectSettingRepository;

    @Autowired
    private ClassLessonRepository classLessonRepository;

    @Override
    public LessonResponseDto findById(Long lessonId) {
        Optional<Lesson> lesson = lessonRepository.findById(lessonId);
        if (!lesson.isPresent()) {
            logger.error("Lesson not found with lesson id: {}", lessonId);
            throw new OnlineLearningException(ErrorTypes.LESSON_NOT_FOUND, lessonId.toString());
        }
        return mappingLesson(lesson.get());
    }

    @Override
    public LessonResponseDto create(LessonRequestDto request) {
        logger.info("Starting create a lesson ...");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request invalid");
            throw new OnlineLearningException(ErrorTypes.REQUEST_MUST_NOT_BE_EMPTY);
        }
        Optional<Subject> existSubject = subjectRepository.findById(request.getSubjectId());
        if (!existSubject.isPresent()) {
            logger.error("Subject not found with subject id: {}", request.getSubjectId());
            throw new OnlineLearningException(ErrorTypes.SUBJECT_WITH_ID_NOT_FOUND, request.getSubjectId().toString());
        }
        Lesson lesson = new Lesson();
        lesson.setName(request.getName());
        lesson.setSubject(existSubject.get());
        lesson.setStatus(request.getStatus());
        lesson.setModuleId(request.getModuleId());
        lesson.setTypeLesson(checkLessonType(request.getTypeLesson()));
        lesson.setDisplayOrder(request.getDisplayOrder());
        lesson.setLessonText(request.getLessonText());
        lesson.setAttachmentUrl(request.getAttachmentUrl());
        lesson.setDuration(request.getDuration());
        lesson.setUpdatedOrder(new Date());
        Test test = null;
        if (request.getTypeLesson().equalsIgnoreCase(Lesson.LESSON_TYPE_NORMAL)) {
            lesson.setLessonText(request.getLessonText());
            lesson.setVideoUrl(request.getVideoUrl());
        } else {
            Optional<Test> existTest = testRepository.findById(request.getTestId());
            if (!existTest.isPresent()) {
                logger.error("Test not found with test id: {}", request.getTestId());
                throw new OnlineLearningException(ErrorTypes.TEST_NOT_FOUND, request.getTestId().toString());
            }
            if (ObjectUtils.isNotEmpty(existTest.get().getSourceId())) {
                logger.error("This test id {} already in quiz lesson", request.getTestId());
                throw new OnlineLearningException(ErrorTypes.TEST_ALREADY_IS_QUIZ_LESSON, request.getTestId().toString());
            }
            lesson.setTestId(request.getTestId());
            test = existTest.get();
        }
        lessonRepository.save(lesson);
        if (ObjectUtils.isNotEmpty(test)) {
            test.setSourceId(lesson.getLessonId());
            testRepository.save(test);
        }
        return mappingLesson(lesson);
    }

    @Override
    public LessonResponseDto update(Long lessonId, LessonRequestDto request) {
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request invalid");
            throw new OnlineLearningException(ErrorTypes.REQUEST_MUST_NOT_BE_EMPTY);
        }
        Optional<Lesson> lesson = lessonRepository.findById(lessonId);
        if (!lesson.isPresent()) {
            logger.error("Lesson not found with lesson id: {}", lessonId);
            throw new OnlineLearningException(ErrorTypes.LESSON_NOT_FOUND, lessonId.toString());
        }
        lesson.get().setName(request.getName());
        lesson.get().setStatus(request.getStatus());
        lesson.get().setModuleId(request.getModuleId());
        lesson.get().setLessonText(request.getLessonText());
        lesson.get().setAttachmentUrl(request.getAttachmentUrl());
        lesson.get().setDuration(request.getDuration());
        if (!Objects.equals(lesson.get().getDisplayOrder(), request.getDisplayOrder())) {
            lesson.get().setDisplayOrder(request.getDisplayOrder());
            lesson.get().setUpdatedOrder(new Date());
        }
        if (!Objects.equals(request.getSubjectId(), lesson.get().getSubject().getSubjectId())) {
            Optional<Subject> existSubject = subjectRepository.findById(request.getSubjectId());
            if (!existSubject.isPresent()) {
                logger.error("Subject not found with subject id: {}", request.getSubjectId());
                throw new OnlineLearningException(ErrorTypes.SUBJECT_WITH_ID_NOT_FOUND, request.getSubjectId().toString());
            }
            lesson.get().setSubject(existSubject.get());
        }
        Test test = null;
        if (request.getTypeLesson().equalsIgnoreCase(Lesson.LESSON_TYPE_NORMAL)) {
            lesson.get().setLessonText(request.getLessonText());
            lesson.get().setVideoUrl(request.getVideoUrl());
        } else {
            if (!Objects.equals(lesson.get().getTestId(), request.getTestId())) {
                Optional<Test> existTest = testRepository.findById(request.getTestId());
                if (!existTest.isPresent()) {
                    logger.error("Test not found with test id: {}", request.getTestId());
                    throw new OnlineLearningException(ErrorTypes.TEST_NOT_FOUND, request.getTestId().toString());
                }
                if (ObjectUtils.isNotEmpty(existTest.get().getSourceId())) {
                    logger.error("This test id {} already in quiz lesson", request.getTestId());
                    throw new OnlineLearningException(ErrorTypes.TEST_ALREADY_IS_QUIZ_LESSON, request.getTestId().toString());
                }
                lesson.get().setTestId(request.getTestId());
                test = existTest.get();
            }
        }
        lessonRepository.save(lesson.get());
        if (ObjectUtils.isNotEmpty(test)) {
            test.setSourceId(lesson.get().getLessonId());
            testRepository.save(test);
        }
        return mappingLesson(lesson.get());
    }

    @Override
    public PaginationResponse findAll(Long testId, Long subjectId, Long moduleId, Boolean status,
                                      String typeLesson, String name, Pageable pageable) {
        PagedListHolder pagedListHolder =
                new PagedListHolder(lessonRepository.findAll(testId, subjectId, moduleId, status, typeLesson, name));
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
    public PaginationResponse getAllBySubjectModule(Long subjectId, Long moduleId, Pageable pageable) {
        List<LessonFilterDto> lessons = lessonRepository.findAll(null, subjectId, moduleId,
                null, null, null);
        List<LessonFilterDto> lessonResponse = lessons.stream()
                .filter(l -> !l.getTypeLesson().equalsIgnoreCase(Lesson.LESSON_TYPE_QUIZ))
                .collect(Collectors.toList());

        PagedListHolder pagedListHolder =
                new PagedListHolder(lessonResponse);
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
    public void delete(Long lessonId) {
        logger.info("Starting delete lesson with lesson id: {}", lessonId);
        Optional<Lesson> lesson = lessonRepository.findById(lessonId);
        if (!lesson.isPresent()) {
            logger.error("Lesson not found with lesson id: {}", lessonId);
            throw new OnlineLearningException(ErrorTypes.LESSON_NOT_FOUND, lessonId.toString());
        }
        lessonRepository.delete(lesson.get());
    }

    @Override
    public List<LessonModuleDto> filter(Long subjectId, Long testId, Long moduleId, Boolean status,
                                        String typeLesson, String name, Pageable pageable) {
        List<LessonFilterDto> lessons = lessonRepository.findAll(testId, subjectId, moduleId, status, typeLesson, name);
        Set<Long> moduleIds = lessons.stream()
                .map(LessonFilterDto::getModuleId)
                .collect(Collectors.toSet());
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userRepository.getIdByUserName(userDetails.getUsername());

        if (ObjectUtils.isEmpty(userId)) {
            logger.error("User not found with user name: {}", userDetails.getUsername());
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND, userDetails.getUsername());
        }
        List<LessonModuleDto> lessonModuleResponse = new ArrayList<>();
        moduleIds.forEach(m -> {
            LessonModuleDto lessonModuleDto = new LessonModuleDto();
            List<LessonFilterDto> lessonFilters = lessons.stream()
                    .filter(l -> Objects.equals(l.getModuleId(), m))
                    .collect(Collectors.toList());
            List<LessonDto> lessonResponse = BeanUtility.mapList(lessonFilters, LessonDto.class);
            List<LessonDto> sortedLessons = lessonResponse.stream()
                    .sorted(lessonComparator())
                    .collect(Collectors.toList());

            Set<Object> roleUser = userRepository.getRole(userId);
            if (CollectionUtils.isNotEmpty(roleUser)) {
                if (roleUser.contains(Setting.USER_ROLE_TRAINEE)) {
                    sortedLessons.forEach(l -> {
                        Optional<StudyResult> studyResult = studyResultRepository.findBySubjectIdAndLessonIdAndUserId(
                                l.getSubjectId(), l.getLessonId(), userId);
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
            lessonModuleDto.setModuleId(sortedLessons.get(0).getModuleId());
            lessonModuleDto.setModuleName(sortedLessons.get(0).getModuleName());
            lessonModuleDto.setStatus(subjectSettingRepository.getStatusSubjectSettingBySubjectSettingId(
                    sortedLessons.get(0).getModuleId()
            ));
            lessonModuleDto.setLessons(sortedLessons);
            lessonModuleResponse.add(lessonModuleDto);
        });
        return lessonModuleResponse;
    }

    @Override
    public List<LessonConfigTestDto> getAllForTestConfig(Long subjectId, Long moduleId) {
        List<Lesson> lessons = lessonRepository.getLessonBySubjectIdAndModuleId(subjectId, moduleId);
        if (CollectionUtils.isEmpty(lessons)) {
            logger.info("Lesson with subject id: {} and module id: {} is empty", subjectId, moduleId);
            return new ArrayList<>();
        }
        List<LessonConfigTestDto> lessonResponse = new ArrayList<>();
        lessons.forEach(l -> {
            LessonConfigTestDto lessonConfig = new LessonConfigTestDto();
            lessonConfig.setLessonId(l.getLessonId());
            lessonConfig.setTypeLesson(l.getTypeLesson());
            lessonConfig.setName(l.getName());
            lessonConfig.setNumberOfQuestion(questionRepository.countQuestionByLessonId(l.getLessonId()));
            lessonResponse.add(lessonConfig);
        });
        return lessonResponse;
    }

    @Override
    public void enable(Long lessonId) {
        logger.info("Starting enable lesson with lesson id: {}", lessonId);
        Optional<Lesson> lesson = lessonRepository.findById(lessonId);
        if (!lesson.isPresent()) {
            logger.error("Lesson not found with lesson id: {}", lessonId);
            throw new OnlineLearningException(ErrorTypes.LESSON_NOT_FOUND, lessonId.toString());
        }
        lesson.get().setStatus(Boolean.TRUE);
        lessonRepository.save(lesson.get());
    }

    @Override
    public void disable(Long lessonId) {
        logger.info("Starting disable lesson with lesson id: {}", lessonId);
        Optional<Lesson> lesson = lessonRepository.findById(lessonId);
        if (!lesson.isPresent()) {
            logger.error("Lesson not found with lesson id: {}", lessonId);
            throw new OnlineLearningException(ErrorTypes.LESSON_NOT_FOUND, lessonId.toString());
        }
        lesson.get().setStatus(Boolean.FALSE);
        lessonRepository.save(lesson.get());
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
        Optional<Lesson> existLesson = lessonRepository.findById(id);
        if (!existLesson.isPresent()) {
            logger.error("Lesson not found with lesson id: {}", id);
            throw new OnlineLearningException(ErrorTypes.LESSON_NOT_FOUND, id.toString());
        }
        logger.info("Get lesson id successfully!");
        Optional<StudyResult> existStudyResult = studyResultRepository.findBySubjectIdAndLessonIdAndUserId(
                existLesson.get().getSubject().getSubjectId(), id, userId
        );
        logger.info("Get lesson id successfully!");
        if (!existStudyResult.isPresent()) {
            Lesson lesson = existLesson.get();
            StudyResult studyResult = new StudyResult();
            studyResult.setUserId(userId);
            studyResult.setIsCompleted(Boolean.TRUE);
            studyResult.setSubjectId(lesson.getSubject().getSubjectId());
            studyResult.setLessonId(lesson.getLessonId());
            logger.info("Start submit data lesson!");
            studyResultService.submitDataLesson(studyResult);
        } else {
            existStudyResult.get().setIsCompleted(Boolean.TRUE);
            studyResultService.submitDataLesson(existStudyResult.get());
        }
    }

    @Override
    public List<NormalLessonResponse> getAllNormalLessonBySubjectId(Long subjectId) {
        List<Lesson> lessons = lessonRepository.findAllNormalLessonBySubjectId(subjectId);
        if (CollectionUtils.isEmpty(lessons)) {
            return new ArrayList<>();
        }
        List<Long> moduleIds = lessons.stream().map(Lesson::getModuleId).collect(Collectors.toList());
        List<SubjectSetting> subjectSettings = subjectSettingRepository.findBySubjectSettingIdIn(moduleIds);
        if (CollectionUtils.isEmpty(subjectSettings)) {
            logger.error("Subject Setting not found by lesson in subject id {}", subjectId);
            throw new OnlineLearningException(ErrorTypes.SUBJECT_SETTING_NOT_FOUND, String.valueOf(subjectId));
        }
        List<NormalLessonResponse> normalLessonResponses = new ArrayList<>();
        lessons.forEach(l -> {
            NormalLessonResponse normalLessonResponse = new NormalLessonResponse();
            normalLessonResponse.setLessonId(l.getLessonId());
            normalLessonResponse.setName(l.getName());
            normalLessonResponse.setModuleId(l.getModuleId());
            subjectSettings.forEach(s -> {
                if (Objects.equals(s.getSubjectSettingId(), l.getModuleId())) {
                    normalLessonResponse.setModuleName(s.getSubjectSettingTitle());
                }
            });
            normalLessonResponses.add(normalLessonResponse);
        });
        return normalLessonResponses;
    }

    @Override
    public List<QuizLessonResponse> getAllQuizLessonBySubjectId(Long subjectId) {
        List<Long> testIds = classLessonRepository.findTestIdIsTrue();
        List<Lesson> lessons = lessonRepository.findAllQuizLessonBySubjectId(subjectId, testIds);
        if (CollectionUtils.isEmpty(lessons)) {
            return new ArrayList<>();
        }
        List<Long> moduleIds = lessons.stream().map(Lesson::getModuleId).collect(Collectors.toList());
        List<SubjectSetting> subjectSettings = subjectSettingRepository.findBySubjectSettingIdIn(moduleIds);
        if (CollectionUtils.isEmpty(subjectSettings)) {
            logger.error("Subject Setting not found by lesson in subject id {}", subjectId);
            throw new OnlineLearningException(ErrorTypes.SUBJECT_SETTING_NOT_FOUND, String.valueOf(subjectId));
        }
        List<QuizLessonResponse> quizLessonResponses = new ArrayList<>();
        lessons.forEach(l -> {
            QuizLessonResponse quizLessonResponse = new QuizLessonResponse();
            quizLessonResponse.setLessonId(l.getLessonId());
            quizLessonResponse.setModuleId(l.getModuleId());
            quizLessonResponse.setTestId(l.getTestId());
            quizLessonResponse.setTestName(testRepository.getTestNameByTestId(l.getTestId()));
            subjectSettings.forEach(s -> {
                if (Objects.equals(s.getSubjectSettingId(), l.getModuleId())) {
                    quizLessonResponse.setModuleName(s.getSubjectSettingTitle());
                }
            });
            quizLessonResponses.add(quizLessonResponse);
        });
        return quizLessonResponses;
    }

    private LessonResponseDto mappingLesson(Lesson lesson) {
        LessonResponseDto lessonResponseDto = new LessonResponseDto();
        lessonResponseDto.setLessonId(lesson.getLessonId());
        lessonResponseDto.setModuleId(lesson.getModuleId());
        lessonResponseDto.setStatus(lesson.getStatus());
        lessonResponseDto.setName(lesson.getName());
        lessonResponseDto.setTypeLesson(lesson.getTypeLesson());
        lessonResponseDto.setVideoUrl(lesson.getVideoUrl());
        lessonResponseDto.setLessonText(lesson.getLessonText());
        lessonResponseDto.setDisplayOrder(lesson.getDisplayOrder());
        lessonResponseDto.setAttachmentUrl(lesson.getAttachmentUrl());
        lessonResponseDto.setDuration(lesson.getDuration());

        SubjectLessonDto subjectLessonDto = new SubjectLessonDto();
        subjectLessonDto.setSubjectId(lesson.getSubject().getSubjectId());
        subjectLessonDto.setSubjectCode(lesson.getSubject().getSubjectCode());
        subjectLessonDto.setSubjectName(lesson.getSubject().getSubjectName());
        subjectLessonDto.setBody(lesson.getSubject().getBody());
        subjectLessonDto.setStatus(lesson.getSubject().getStatus());
        subjectLessonDto.setCategoryId(lesson.getSubject().getCategoryId());

        lessonResponseDto.setSubject(subjectLessonDto);
        if (lesson.getTypeLesson().equalsIgnoreCase(Lesson.LESSON_TYPE_QUIZ)) {
            if (ObjectUtils.isEmpty(lesson.getTestId())) {
                logger.error("Quiz lesson not assigned any test!");
                throw new OnlineLearningException(ErrorTypes.QUIZ_LESSON_NOT_ASSIGNED_ANY_TEST, lesson.getLessonId().toString());
            }
            Optional<Test> test = testRepository.findById(lesson.getTestId());
            if (test.isPresent()) {
                lessonResponseDto.setTestId(lesson.getTestId());
                lessonResponseDto.setTestType(test.get().getTestType());
                lessonResponseDto.setTestTitle(test.get().getName());
                lessonResponseDto.setPassRate(test.get().getPassRate());
                lessonResponseDto.setNumberOfQuestion(testService.countTotalQuestionInTestQuizLessonAndPracticeTest(test.get()));
            }
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userRepository.getIdByUserName(userDetails.getUsername());

        if (ObjectUtils.isEmpty(userId)) {
            logger.error("User not found with user name: {}", userDetails.getUsername());
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND, userDetails.getUsername());
        }
        Optional<StudyResult> studyResult = studyResultRepository.findBySubjectIdAndLessonIdAndUserId(
                lesson.getSubject().getSubjectId(), lesson.getLessonId(), userId);
        if (studyResult.isPresent()) {
            if (ObjectUtils.isNotEmpty(studyResult.get().getIsCompleted()) && studyResult.get().getIsCompleted()) {
                lessonResponseDto.setIsCompleted(Boolean.TRUE);
            }
            if (ObjectUtils.isNotEmpty(studyResult.get().getIsPassed()) && studyResult.get().getIsPassed()) {
                lessonResponseDto.setIsCompleted(Boolean.TRUE);
            }
        }
        return lessonResponseDto;
    }

    private Comparator<LessonDto> lessonComparator() {
        return Comparator
                .comparing(LessonDto::getDisplayOrder)
                .thenComparing(LessonDto::getUpdatedOrder, Comparator.nullsFirst(Comparator.naturalOrder()));
    }

    private String checkLessonType(String lessonType) {
        if (lessonType.equalsIgnoreCase(Lesson.LESSON_TYPE_QUIZ)) {
            return Lesson.LESSON_TYPE_QUIZ;
        }
        if (lessonType.equalsIgnoreCase(Lesson.LESSON_TYPE_NORMAL)) {
            return Lesson.LESSON_TYPE_NORMAL;
        }
        logger.error("Lesson not match any type: {}", lessonType);
        throw new OnlineLearningException(ErrorTypes.LESSON_NOT_MATCH_ANY_TYPE, lessonType);
    }
}
