package swp490.g7.OnlineLearningSystem.entities.packages.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.ClassLesson;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.repository.ClassLessonRepository;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.Lesson;
import swp490.g7.OnlineLearningSystem.entities.lesson.repository.LessonRepository;
import swp490.g7.OnlineLearningSystem.entities.packages.domain.Pack;
import swp490.g7.OnlineLearningSystem.entities.packages.domain.dto.GradesClassDto;
import swp490.g7.OnlineLearningSystem.entities.packages.domain.dto.GradesDto;
import swp490.g7.OnlineLearningSystem.entities.packages.domain.dto.LearningProgressDto;
import swp490.g7.OnlineLearningSystem.entities.packages.domain.response.PackResponseDto;
import swp490.g7.OnlineLearningSystem.entities.packages.repository.PackRepository;
import swp490.g7.OnlineLearningSystem.entities.packages.service.PackService;
import swp490.g7.OnlineLearningSystem.entities.study_result.domain.StudyResult;
import swp490.g7.OnlineLearningSystem.entities.study_result.repository.StudyResultRepository;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.Subject;
import swp490.g7.OnlineLearningSystem.entities.subject.repository.SubjectRepository;
import swp490.g7.OnlineLearningSystem.entities.test.domain.Test;
import swp490.g7.OnlineLearningSystem.entities.test.repository.TestRepository;
import swp490.g7.OnlineLearningSystem.entities.user.repository.UserRepository;
import swp490.g7.OnlineLearningSystem.entities.user_package.repository.UserPackageRepository;
import swp490.g7.OnlineLearningSystem.errorhandling.CommonErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
import swp490.g7.OnlineLearningSystem.utilities.BeanUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class PackServiceImpl implements PackService {
    private static final Logger logger = LogManager.getLogger(PackServiceImpl.class);

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private PackRepository packRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private StudyResultRepository studyResultRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPackageRepository userPackageRepository;

    @Autowired
    private ClassLessonRepository classLessonRepository;

    @Override
    public List<Pack> findAll() {
        return packRepository.findAll();
    }

    @Override
    public LearningProgressDto getLearningProgress(Long subjectId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userRepository.getIdByUserName(userDetails.getUsername());

        if (ObjectUtils.isEmpty(userId)) {
            logger.error("User not found with user name: {}", userDetails.getUsername());
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND, userDetails.getUsername());
        }
        long total = lessonRepository.countTotalLessonBySubjectId(subjectId);
        long isCompleted = studyResultRepository.countLessonUserCompleted(subjectId, userId);
        Double percent = percentCalculator(total, isCompleted);
        logger.info(percent);
        LearningProgressDto learningProgressDto = new LearningProgressDto();
        learningProgressDto.setTotal(total);
        learningProgressDto.setIsCompleted(isCompleted);
        learningProgressDto.setPercent(percent);
        return learningProgressDto;
    }

    @Transactional
    @Override
    public List<GradesDto> getGradeLessons(Long subjectId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userRepository.getIdByUserName(userDetails.getUsername());

        if (ObjectUtils.isEmpty(userId)) {
            logger.error("User not found with user name: {}", userDetails.getUsername());
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND, userDetails.getUsername());
        }
        List<Lesson> quizLessons = lessonRepository.getLessonQuizBySubjectId(subjectId);
        if (CollectionUtils.isEmpty(quizLessons)) {
            logger.error("Quiz lesson is empty with subject id {}", subjectId);
            return new ArrayList<>();
        }
        List<Long> testIds = quizLessons.stream()
                .map(Lesson::getTestId)
                .collect(Collectors.toList());
        List<Test> tests = testRepository.findByTestIdIn(testIds);
        if (CollectionUtils.isEmpty(tests)) {
            logger.error("Test not found with subject id {}", subjectId);
            throw new OnlineLearningException(ErrorTypes.TEST_NOT_FOUND, subjectId.toString());
        }
        List<StudyResult> studyResults = studyResultRepository.getLessonQuizStudyResultByUserIdAndSubjectId(subjectId, userId);
        List<GradesDto> grades = new ArrayList<>();
        quizLessons.forEach(lesson -> {
            GradesDto grade = new GradesDto();
            grade.setLessonId(lesson.getLessonId());
            grade.setTypeLesson(lesson.getTypeLesson());
            grade.setLessonName(lesson.getName());
            tests.forEach(t -> {
                if (Objects.equals(t.getTestId(), lesson.getTestId())) {
                    grade.setPassRate(t.getPassRate());
                    grade.setDuration(t.getDuration());
                    AtomicInteger numberOfQuestion = new AtomicInteger();
                    t.getTestConfig().forEach(tc -> {
                        numberOfQuestion.addAndGet(tc.getQuantity());
                    });
                    grade.setTotalOfQuestion(numberOfQuestion.get());
                }
            });
            if (CollectionUtils.isNotEmpty(studyResults)) {
                studyResults.forEach(s -> {
                    if (Objects.equals(lesson.getLessonId(), s.getLessonId())) {
                        grade.setGrade(s.getLessonResult());
                        grade.setIsPassed(s.getIsPassed());
                    }
                });
            }
            grades.add(grade);
        });
        return grades;
    }

    @Override
    public List<PackResponseDto> getCurrentPackageByUser(Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userRepository.getIdByUserName(userDetails.getUsername());

        if (ObjectUtils.isEmpty(userId)) {
            logger.error("User not found with user name: {}", userDetails.getUsername());
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND, userDetails.getUsername());
        }
        Optional<Subject> existSubject = subjectRepository.findById(id);
        if (!existSubject.isPresent()) {
            logger.error("Subject not found with subject id: {}", id);
            throw new OnlineLearningException(ErrorTypes.SUBJECT_WITH_ID_NOT_FOUND, id.toString());
        }
        List<Long> packageIds = userPackageRepository.findListPackageIdByUserId(userId);
        List<Pack> packs = packRepository.findAllByPackageIdInAndSubjectId(packageIds, id);
        return BeanUtility.mapList(packs, PackResponseDto.class);
    }

    @Override
    public List<GradesClassDto> getGradeClassLessons(Long subjectId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userRepository.getIdByUserName(userDetails.getUsername());

        if (ObjectUtils.isEmpty(userId)) {
            logger.error("User not found with user name: {}", userDetails.getUsername());
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND, userDetails.getUsername());
        }
        List<ClassLesson> quizClassLessons = classLessonRepository.getClassLessonQuizBySubjectId(subjectId);
        if (CollectionUtils.isEmpty(quizClassLessons)) {
            logger.error("Quiz lesson is empty with subject id {}", subjectId);
            return new ArrayList<>();
        }
        List<Long> testIds = quizClassLessons.stream()
                .map(ClassLesson::getTestId)
                .collect(Collectors.toList());
        List<Test> tests = testRepository.findByTestIdIn(testIds);
        if (CollectionUtils.isEmpty(tests)) {
            logger.error("Test not found with subject id {}", subjectId);
            throw new OnlineLearningException(ErrorTypes.TEST_NOT_FOUND, subjectId.toString());
        }
        List<StudyResult> studyResults = studyResultRepository.getClassLessonQuizStudyResultByUserIdAndSubjectId(subjectId, userId);
        List<GradesClassDto> grades = new ArrayList<>();
        quizClassLessons.forEach(cl -> {
            GradesClassDto grade = new GradesClassDto();
            grade.setClassLessonId(cl.getClassLessonId());
            grade.setTypeLesson(cl.getTypeLesson());
            grade.setClassLessonName(cl.getName());
            tests.forEach(t -> {
                if (Objects.equals(t.getTestId(), cl.getTestId())) {
                    grade.setPassRate(t.getPassRate());
                    grade.setDuration(t.getDuration());
                    AtomicInteger numberOfQuestion = new AtomicInteger();
                    t.getTestConfig().forEach(tc -> {
                        numberOfQuestion.addAndGet(tc.getQuantity());
                    });
                    grade.setTotalOfQuestion(numberOfQuestion.get());
                }
            });
            if (CollectionUtils.isNotEmpty(studyResults)) {
                studyResults.forEach(s -> {
                    if (Objects.equals(cl.getClassLessonId(), s.getClassLessonId())) {
                        grade.setGrade(s.getLessonResult());
                        grade.setIsPassed(s.getIsPassed());
                    }
                });
            }
            grades.add(grade);
        });
        return grades;
    }

    private Double percentCalculator(long total, long number) {
        try {
            return ((double) number / (double) total) * 100;
        } catch (Exception e) {
            logger.error("Failed to calculate percent!");
            throw new OnlineLearningException(CommonErrorTypes.INTERNAL_SERVER_ERROR);
        }
    }
}
