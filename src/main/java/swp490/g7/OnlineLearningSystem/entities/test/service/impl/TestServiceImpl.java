package swp490.g7.OnlineLearningSystem.entities.test.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.ClassLesson;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.repository.ClassLessonRepository;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.ContentGroup;
import swp490.g7.OnlineLearningSystem.entities.content_group.repository.ContentGroupRepository;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.Lesson;
import swp490.g7.OnlineLearningSystem.entities.lesson.repository.LessonRepository;
import swp490.g7.OnlineLearningSystem.entities.question.domain.AnswerOption;
import swp490.g7.OnlineLearningSystem.entities.question.domain.Question;
import swp490.g7.OnlineLearningSystem.entities.question.domain.response.AnswerOptionResponseDto;
import swp490.g7.OnlineLearningSystem.entities.question.repository.QuestionRepository;
import swp490.g7.OnlineLearningSystem.entities.study_result.domain.StudyResult;
import swp490.g7.OnlineLearningSystem.entities.study_result.repository.StudyResultRepository;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.Subject;
import swp490.g7.OnlineLearningSystem.entities.subject.repository.SubjectRepository;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.domain.SubjectSetting;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.repository.SubjectSettingRepository;
import swp490.g7.OnlineLearningSystem.entities.test.domain.Test;
import swp490.g7.OnlineLearningSystem.entities.test.domain.TestConfig;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.TestConfigCustomDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.TestDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.TestFilterDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.TestTypeDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.ResultAnalysisReview;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.TestResultReview;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.other.TestHistoryDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.other.TestResultDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.ContentGroupTotal;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.PrePareLessonConfigDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.QuestionContentQueryDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.QuizLessonTotal;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.ResultTestDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.TestConfigDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.TestQuestionRandomDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.TestRandomResponseDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.request.TestRequestDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.response.TestResponseCustomDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.response.TestResponseDto;
import swp490.g7.OnlineLearningSystem.entities.test.repository.TestRepository;
import swp490.g7.OnlineLearningSystem.entities.test.service.TestService;
import swp490.g7.OnlineLearningSystem.entities.user.domain.User;
import swp490.g7.OnlineLearningSystem.entities.user.repository.UserRepository;
import swp490.g7.OnlineLearningSystem.entities.user_test.domain.TestAnalysis;
import swp490.g7.OnlineLearningSystem.entities.user_test.domain.UserAnswer;
import swp490.g7.OnlineLearningSystem.entities.user_test.domain.UserAnswerId;
import swp490.g7.OnlineLearningSystem.entities.user_test.domain.UserTest;
import swp490.g7.OnlineLearningSystem.entities.user_test.repository.TestAnalysisRepository;
import swp490.g7.OnlineLearningSystem.entities.user_test.repository.UserAnswerRepository;
import swp490.g7.OnlineLearningSystem.entities.user_test.repository.UserTestRepository;
import swp490.g7.OnlineLearningSystem.errorhandling.CommonErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
import swp490.g7.OnlineLearningSystem.utilities.BeanUtility;
import swp490.g7.OnlineLearningSystem.utilities.Constants;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class TestServiceImpl implements TestService {

    private static final Logger logger = LogManager.getLogger(TestService.class);

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserTestRepository userTestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContentGroupRepository contentGroupRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private StudyResultRepository studyResultRepository;

    @Autowired
    private TestAnalysisRepository testAnalysisRepository;

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Autowired
    private SubjectSettingRepository subjectSettingRepository;

    @Autowired
    private ClassLessonRepository classLessonRepository;


    @Override
    public PaginationResponse filter(Long subjectId, Integer testType, String name, Boolean status, Pageable pageable) {
        PagedListHolder pagedListHolder =
                new PagedListHolder(testRepository.filter(subjectId, testType, name, status));
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
    public PaginationResponse getAllBySubject(Long id, Pageable pageable) {
        PagedListHolder pagedListHolder =
                new PagedListHolder(testRepository.getAllBySubject(id));
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
    public TestResponseCustomDto findById(Long testId, Integer type) {
        Optional<Test> test = testRepository.findById(testId);
        if (!test.isPresent()) {
            logger.error("Test not found with test id: {}", testId);
            throw new OnlineLearningException(ErrorTypes.TEST_NOT_FOUND, testId.toString());
        }
        Test testData = test.get();
        TestResponseCustomDto testResponseCustomDto = new TestResponseCustomDto();
        testResponseCustomDto.setTestId(testData.getTestId());
        testResponseCustomDto.setName(testData.getName());
        testResponseCustomDto.setDuration(testData.getDuration());
        testResponseCustomDto.setPassRate(testData.getPassRate());
        testResponseCustomDto.setStatus(testData.getStatus());
        testResponseCustomDto.setTestType(testData.getTestType());
        testResponseCustomDto.setSubjectId(testData.getSubject().getSubjectId());
        if (checkTestType(testData.getTestType())) {
            testResponseCustomDto.setNumberOfQuestion(countTotalQuestionInTestAnotherType(testData));
        } else {
            testResponseCustomDto.setNumberOfQuestion(countTotalQuestionInTestQuizLessonAndPracticeTest(testData));
        }
        if (CollectionUtils.isNotEmpty(testData.getTestConfig())) {
            List<TestConfigCustomDto> testConfigCustoms = new ArrayList<>();
            testData.getTestConfig().forEach(t -> {
                TestConfigCustomDto testConfigCustomDto = new TestConfigCustomDto();
                testConfigCustomDto.setTestId(t.getTest().getTestId());
                testConfigCustomDto.setId(t.getId());
                testConfigCustomDto.setQuantity(t.getQuantity());
                if (ObjectUtils.isNotEmpty(t.getGroupId())) {
                    testConfigCustomDto.setGroupId(t.getGroupId());
                    testConfigCustomDto.setContentGroupTitle(contentGroupRepository.getNameContentGroupById(t.getGroupId()));
                }
                if (ObjectUtils.isNotEmpty(t.getLessonId())) {
                    testConfigCustomDto.setLessonId(t.getLessonId());
                    testConfigCustomDto.setLessonName(lessonRepository.getNameLessonById(t.getLessonId()));
                }
                testConfigCustoms.add(testConfigCustomDto);
            });
            testResponseCustomDto.setTestConfigs(testConfigCustoms);
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        if (!user.isPresent()) {
            logger.error("User not found with user name: {}", userDetails.getUsername());
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND, userDetails.getUsername());
        }
        Optional<UserTest> userTest;
        switch (type) {
            case Lesson.LESSON_QUIZ_TYPE:
                userTest = userTestRepository.findByTestAndUserIdAndClassLessonIdIsNull(test.get(), user.get().getUserId());
                break;
            case ClassLesson.CLASS_LESSON_QUIZ_TYPE:
                userTest = userTestRepository.findByTestAndUserIdAndLessonIdIsNull(test.get(), user.get().getUserId());
                break;
            default:
                logger.error("Type of quiz must be define!");
                throw new OnlineLearningException(CommonErrorTypes.INVALID_INPUT);
        }
        if (!userTest.isPresent()) {
            return testResponseCustomDto;
        }
        TestResultDto testResult = new TestResultDto();
        testResult.setUserTestId(userTest.get().getUserTestId());
        testResult.setCorrects(userTest.get().getCorrects());
        testResult.setStartedDate(userTest.get().getStartedDate());
        testResult.setCorrects(userTest.get().getCorrects());
        testResult.setResult(userTest.get().getScorePercent());
        testResult.setTotal(userTest.get().getTotal());
        testResult.setIsPassed(checkPassed(userTest.get().getScorePercent(), test.get().getPassRate()));
        testResponseCustomDto.setTestResult(testResult);
        return testResponseCustomDto;
    }

    @Override
    public TestResponseDto create(TestRequestDto request) {
        logger.info("Start creating a test...");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Invalid request!");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }
        Optional<Subject> subject = subjectRepository.findById(request.getSubjectId());
        if (!subject.isPresent()) {
            logger.error("Subject not found with subject id: {}", request.getSubjectId());
            throw new OnlineLearningException(ErrorTypes.SUBJECT_WITH_ID_NOT_FOUND, request.getSubjectId().toString());
        }
        Test test = BeanUtility.convertValue(request, Test.class);
        test.setSubject(subject.get());
        if (!checkTestType(request.getTestType())) {
            List<TestConfig> testConfigs = BeanUtility.mapList(request.getTestConfigs(), TestConfig.class);
            testConfigs.forEach(t -> t.setTest(test));
            test.setTestConfig(testConfigs);
        }
        testRepository.save(test);
        logger.info("Successfully create a test...");
        return mapTestDto(test);
    }

    @Override
    public TestResponseDto update(Long testId, TestRequestDto request) {
        logger.info("Start updating a test...");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Invalid request!");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }
        Optional<Test> test = testRepository.findById(testId);
        if (!test.isPresent()) {
            logger.error("Test not found with test id : {}", testId);
            throw new OnlineLearningException(ErrorTypes.TEST_NOT_FOUND, testId.toString());
        }
        Optional<Subject> subject = subjectRepository.findById(request.getSubjectId());
        if (!subject.isPresent()) {
            logger.error("Subject not found with subject id: {}", request.getSubjectId());
            throw new OnlineLearningException(ErrorTypes.SUBJECT_WITH_ID_NOT_FOUND, request.getSubjectId().toString());
        }
        test.get().setSubject(subject.get());
        test.get().setDuration(request.getDuration());
        test.get().setName(request.getName());
        test.get().setPassRate(request.getPassRate());
        testRepository.save(test.get());
        logger.info("Successfully update a test...");
        return mapTestDto(test.get());
    }

    @Override
    public void delete(Long testId) {
        Optional<Test> test = testRepository.findById(testId);
        if (!test.isPresent()) {
            logger.error("Test not found with test id: {}", testId);
            throw new OnlineLearningException(ErrorTypes.TEST_NOT_FOUND, testId.toString());
        }
        testRepository.delete(test.get());
    }

    @Override
    public List<TestTypeDto> getAllTestTypeWithTest(Long subjectId) {
        List<TestFilterDto> testFilters = testRepository.filter(subjectId, null, null, Boolean.TRUE);
        List<TestFilterDto> mockTest = testFilters.stream()
                .filter(t -> Objects.equals(t.getTestType(), Test.MOCK_TEST))
                .collect(Collectors.toList());
        List<TestFilterDto> fullTest = testFilters.stream()
                .filter(t -> Objects.equals(t.getTestType(), Test.FULL_TEST))
                .collect(Collectors.toList());

        List<TestTypeDto> testResponses = new ArrayList<>();
        TestTypeDto typeMock = new TestTypeDto();
        typeMock.setTestType(Test.MOCK_TEST);
        typeMock.setTests(BeanUtility.mapList(mockTest, TestDto.class));
        testResponses.add(typeMock);

        TestTypeDto typeFull = new TestTypeDto();
        typeFull.setTestType(Test.FULL_TEST);
        typeFull.setTests(BeanUtility.mapList(fullTest, TestDto.class));
        testResponses.add(typeFull);
        return testResponses;
    }

    @Override
    public List<TestFilterDto> getAllTestByTestType(Long subjectId, Integer testType) {
        List<TestFilterDto> testFilters = testRepository.filter(subjectId, testType, null, Boolean.TRUE);
        return testFilters.stream()
                .filter(t -> ObjectUtils.isEmpty(t.getSourceId()))
                .collect(Collectors.toList());
    }

    @Override
    public Integer countTotalQuestionInTestQuizLessonAndPracticeTest(Test test) {
        if (ObjectUtils.isEmpty(test)) {
            logger.info("Test not found");
            return 0;
        }
        AtomicReference<Integer> total = new AtomicReference<>(0);
        List<TestConfig> testConfigs = test.getTestConfig();
        testConfigs.forEach(t -> {
            total.updateAndGet(v -> v + t.getQuantity());
        });
        return total.get();
    }

    @Override
    public Integer countTotalQuestionInTestAnotherType(Test test) {
        if (ObjectUtils.isEmpty(test)) {
            logger.info("Test not found");
            return 0;
        }
        long total = questionRepository.countQuestionByTestId(test.getTestId());
        return (int) total;
    }

    @Override
    public List<TestHistoryDto> getTestHistory(Long subjectId, String name) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());

        if (!user.isPresent()) {
            logger.error("User not found with user name: {}", userDetails.getUsername());
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND, userDetails.getUsername());
        }
        if (ObjectUtils.isEmpty(name)) {
            name = Constants.BLANK_STRING;
        }
        List<UserTest> userTests = userTestRepository.findByTestPracticeAndUserId(subjectId, user.get().getUserId(), name);
        if (CollectionUtils.isEmpty(userTests)) {
            return new ArrayList<>();
        }
        List<TestHistoryDto> testHistories = new ArrayList<>();
        userTests.forEach(u -> {
            TestHistoryDto testHistory = mapTestHistory(u.getTest());
            testHistory.setUserTestId(u.getUserTestId());
            testHistory.setStartedDate(u.getStartedDate());
            testHistory.setResult(u.getScorePercent());
            testHistory.setCorrects(u.getCorrects());
            testHistory.setNumberOfQuestion(u.getTotal());
            testHistory.setTotalTime(u.getTotalTime());
            testHistory.setIsPassed(checkPassed(u.getScorePercent(), u.getTest().getPassRate()));
            testHistories.add(testHistory);
        });
        return testHistories.stream()
                .sorted(Comparator.comparing(TestHistoryDto::getStartedDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public TestRandomResponseDto getPractice(Long testId) {
        Optional<Test> test = testRepository.findById(testId);
        if (!test.isPresent()) {
            logger.error("Test not found with test id: {}", testId);
            throw new OnlineLearningException(ErrorTypes.TEST_NOT_FOUND, testId.toString());
        }
        PrePareLessonConfigDto p = getPrepareLessonConfig(test.get());

        if (CollectionUtils.isNotEmpty(p.getLessonTotals())) {
            return getQuestionByIdInAndConvertToTestResponseDto(test.get(), testRepository.getListQuestionLesson(p),
                    test.get().getTestType());
        }
        return getQuestionAndConvertToTestResponseCaseContentGroup(test.get(), testRepository.getListQuestionWithContentGroup(p),
                test.get().getTestType());
    }

    @Override
    public TestRandomResponseDto getSimulationAndDemo(Long testId) {
        Optional<Test> existTest = testRepository.findById(testId);
        if (!existTest.isPresent()) {
            logger.error("Test not found with test id: {}", testId);
            throw new OnlineLearningException(ErrorTypes.TEST_NOT_FOUND, testId.toString());
        }
        Test test = existTest.get();
        List<Question> questions = questionRepository.findByTestIdAndSubject(test.getTestId(), test.getSubject());
        if (CollectionUtils.isEmpty(questions)) {
            logger.error("Question not found by test id: {}", testId);
            throw new OnlineLearningException(ErrorTypes.QUESTION_NOT_FOUND, testId.toString());
        }
        TestRandomResponseDto testRandomResponse = new TestRandomResponseDto();
        testRandomResponse.setTestId(test.getTestId());
        testRandomResponse.setTestType(test.getTestType());
        testRandomResponse.setDuration(test.getDuration());
        testRandomResponse.setName(test.getName());
        testRandomResponse.setPassRate(test.getPassRate());
        //Convert list question to Question response dto
        List<TestQuestionRandomDto> questionResponses = new ArrayList<>();
        questions.forEach(q -> {
            questionResponses.add(convertQuestionToDto(q, null, Boolean.FALSE));
        });
        testRandomResponse.setQuestions(questionResponses);
        return testRandomResponse;
    }

    @Override
    public ResultTestDto getAnswerSimulationAndDemo(Long testId, TestRandomResponseDto request) {
        Optional<Test> existTest = testRepository.findById(testId);
        if (!existTest.isPresent()) {
            logger.error("Test not found with test id: {}", testId);
            throw new OnlineLearningException(ErrorTypes.TEST_NOT_FOUND, testId.toString());
        }
        Test test = existTest.get();
        List<Question> questions = questionRepository.findByTestIdAndSubject(test.getTestId(), test.getSubject());
        List<TestQuestionRandomDto> listCorrectAnswer = new ArrayList<>();
        questions.forEach(q -> {
            listCorrectAnswer.add(convertQuestionToDto(q, null, Boolean.TRUE));
        });
        List<TestQuestionRandomDto> listUserAnswer = request.getQuestions();
        listCorrectAnswer.sort(Comparator.comparing(TestQuestionRandomDto::getQuestionId));
        listUserAnswer.sort(Comparator.comparing(TestQuestionRandomDto::getQuestionId));
        int score = 0;
        int numberQuestion = listUserAnswer.size();
        List<TestQuestionRandomDto> listResult = new ArrayList<>();
        ResultTestDto resultTestDto = new ResultTestDto();
        for (int i = 0; i < numberQuestion; i++) {
            TestQuestionRandomDto userAnswerResult = compareAnswer(listCorrectAnswer.get(i), listUserAnswer.get(i));
            if (userAnswerResult.getIsCorrect().equals(Boolean.TRUE)) {
                score++;
            }
            listResult.add(userAnswerResult);
        }
        double resultPercentage = ((double) score / (double) numberQuestion) * 100;
        resultTestDto.setTotalOfQuestion(numberQuestion);
        resultTestDto.setQuestionResult(listResult);
        resultTestDto.setNumberCorrect(score);
        resultTestDto.setScorePercent(Math.floor(resultPercentage * 100) / 100);
        resultTestDto.setIsPass(checkPassed(resultTestDto.getScorePercent(), test.getPassRate()));
        if (ObjectUtils.isNotEmpty(request.getTotalTimeSeconds())) {
            resultTestDto.setTotalTime((double) request.getTotalTimeSeconds() / 60);
            resultTestDto.setSecondPerQuestion((double) request.getTotalTimeSeconds() / (double) numberQuestion);
        } else {
            resultTestDto.setTotalTime(0);
            resultTestDto.setSecondPerQuestion(0);
        }
        if (!Objects.equals(test.getTestType(), Test.DEMO_TEST)) {
            saveTestResultForSimulationAndDemo(test, resultTestDto, request);
        }
        return resultTestDto;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void saveTestResultForSimulationAndDemo(Test test, ResultTestDto resultTestDto, TestRandomResponseDto testRandom) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userRepository.getIdByUserName(userDetails.getUsername());

        if (ObjectUtils.isEmpty(userId)) {
            logger.error("User not found with user name: {}", userDetails.getUsername());
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND, userDetails.getUsername());
        }
        //User test
        Optional<UserTest> existUserTest = userTestRepository.findByTestAndUserIdAndClassLessonIdIsNull(test, userId);
        UserTest userTest = new UserTest();
        if (existUserTest.isPresent()) {
            userTest = existUserTest.get();
        }
        userTest.setTest(test);
        userTest.setLessonId(test.getSourceId());
        userTest.setStartedDate(new Date());
        userTest.setTotalTime(resultTestDto.getTotalTime());
        userTest.setSecondPerQuestion(resultTestDto.getSecondPerQuestion());
        userTest.setUserId(userId);
        userTest.setCorrects(resultTestDto.getNumberCorrect());
        userTest.setTotal(resultTestDto.getTotalOfQuestion());
        userTest.setScorePercent(resultTestDto.getScorePercent());
        userTest.setIsPassed(resultTestDto.getIsPass());
        userTestRepository.save(userTest);
    }

    @Override
    public TestResultReview getResultTest(Long testId, Integer type) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userRepository.getIdByUserName(userDetails.getUsername());

        if (ObjectUtils.isEmpty(userId)) {
            logger.error("User not found with user name: {}", userDetails.getUsername());
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND, userDetails.getUsername());
        }
        Optional<Test> existTest = testRepository.findById(testId);
        if (!existTest.isPresent()) {
            logger.error("Test not found with test id: {}", testId);
            throw new OnlineLearningException(ErrorTypes.TEST_NOT_FOUND, testId.toString());
        }
        Test test = existTest.get();
        TestResultReview testResultReview = new TestResultReview();
        Optional<UserTest> existUserTest;
        switch (type) {
            case Lesson.LESSON_QUIZ_TYPE:
                existUserTest = userTestRepository.findByTestAndUserIdAndClassLessonIdIsNull(test, userId);
                break;
            case ClassLesson.CLASS_LESSON_QUIZ_TYPE:
                existUserTest = userTestRepository.findByTestAndUserIdAndLessonIdIsNull(test, userId);
                break;
            default:
                logger.error("Type of quiz must be define!");
                throw new OnlineLearningException(CommonErrorTypes.INVALID_INPUT);
        }
        if (!existUserTest.isPresent()) {
            logger.error("Don't have data test with user idd {}, and test id {}", userId, testId);
            throw new OnlineLearningException(ErrorTypes.THIS_ACCOUNT_NO_HAVE_DATA_FOR_THIS_TEST, testId.toString());
        }
        UserTest userTest = existUserTest.get();
        testResultReview.setTestId(testId);
        testResultReview.setTestType(test.getTestType());
        testResultReview.setNumberCorrect(userTest.getCorrects());
        testResultReview.setScorePercent(userTest.getScorePercent());
        testResultReview.setIsPass(userTest.getIsPassed());
        testResultReview.setTotalOfQuestion(userTest.getTotal());
        testResultReview.setTotalTime(userTest.getTotalTime());
        testResultReview.setSecondPerQuestion(userTest.getSecondPerQuestion());
        if (checkTestType(test.getTestType())) {
            return testResultReview;
        }
        List<UserAnswer> existUserAnswers = userAnswerRepository.findAllByUserTest(userTest);
        List<Long> questionIds = new ArrayList<>();
        existUserAnswers.forEach(e -> {
            questionIds.add(e.getUserAnswerId().getQuestionId());
        });
        TestRandomResponseDto testRandom = new TestRandomResponseDto();
        if (test.getTestType().equals(Test.QUIZ_LESSON)) {
            testRandom = getQuestionByIdInAndConvertToTestResponseDto(test, questionIds, Test.QUIZ_LESSON);
        }
        if (test.getTestType().equals(Test.PRACTICE_TEST)) {
            testRandom = getQuestionByIdInAndConvertToTestResponseDto(test, questionIds, Test.PRACTICE_TEST);
        }
        List<TestQuestionRandomDto> listCorrectAnswer = testRandom.getQuestions();
        listCorrectAnswer.sort(Comparator.comparing(TestQuestionRandomDto::getQuestionId));

        List<String> tempAnswerIds = new ArrayList<>();
        existUserAnswers.forEach(ua -> {
            String[] temp = ua.getAnswers().split(",");
            for (int i = 0; i < temp.length; i++) {
                if (ObjectUtils.isNotEmpty(temp[i]) && StringUtils.isNotBlank(temp[i])) {
                    tempAnswerIds.add(temp[i]);
                }
            }
        });
        List<Long> answerIds = tempAnswerIds.stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());
        listCorrectAnswer.forEach(ca -> {
            ca.getAnswerOptions().forEach(a -> {
                if (answerIds.contains(a.getId())) {
                    a.setUserAnswer(Boolean.TRUE);
                }
            });
        });
        listCorrectAnswer.forEach(ca -> {
            existUserAnswers.forEach(ua -> {
                if (Objects.equals(ua.getQuestion().getQuestionId(), ca.getQuestionId())) {
                    ca.setIsCorrect(ua.getIsCorrect());
                    if (ObjectUtils.isNotEmpty(ua.getGroupId())) {
                        ca.setGroupId(ua.getGroupId());
                    }
                    if (ObjectUtils.isNotEmpty(ua.getMarked())) {
                        ca.setMarked(ua.getMarked());
                    }
                }
            });
        });
        List<TestAnalysis> testAnalyses = testAnalysisRepository.findAllByUserTestId(userTest.getUserTestId());
        if (CollectionUtils.isEmpty(testAnalyses)) {
            logger.error("Test analyses for test id {} not found!", testId);
            throw new OnlineLearningException(ErrorTypes.RESULT_ANALYSES_NOT_FOUND, testId.toString());
        }
        List<ResultAnalysisReview> resultAnalysisReviews = new ArrayList<>();
        testAnalyses.forEach(ta -> {
            ResultAnalysisReview resultAnalysisReview = new ResultAnalysisReview();
            resultAnalysisReview.setId(ta.getTypeId());
            resultAnalysisReview.setPercent(ta.getPercent());
            resultAnalysisReview.setTypeName(ta.getTypeName());
            test.getTestConfig().forEach(t -> {
                if (ta.getTestConfigType().equalsIgnoreCase(TestConfig.LESSON_CONFIG_TYPE)) {
                    if (Objects.equals(t.getLessonId(), ta.getTypeId())) {
                        resultAnalysisReview.setTotalQuestion(t.getQuantity());
                        resultAnalysisReview.setTotalCorrects((int) Math.ceil((ta.getPercent() * t.getQuantity()) / 100));
                    }
                } else {
                    if (Objects.equals(t.getGroupId(), ta.getTypeId())) {
                        resultAnalysisReview.setTotalQuestion(t.getQuantity());
                        resultAnalysisReview.setTotalCorrects((int) Math.ceil((ta.getPercent() * t.getQuantity()) / 100));
                    }
                }
            });
            resultAnalysisReviews.add(resultAnalysisReview);
        });
        listCorrectAnswer.forEach(ca -> {
            if (ObjectUtils.isNotEmpty(ca.getGroupId())) {
                testAnalyses.forEach(ta -> {
                    if (Objects.equals(ta.getTypeId(), ca.getGroupId())) {
                        ca.setDomain(ta.getTypeName());
                    }
                });
            }
        });
        Long moduleId = null;
        if (testAnalyses.get(0).getTestConfigType().equalsIgnoreCase(TestConfig.LESSON_CONFIG_TYPE)) {
            if (ObjectUtils.isNotEmpty(test.getTestConfig().get(0).getLessonId())) {
                Optional<Lesson> lesson = lessonRepository.findById(test.getTestConfig().get(0).getLessonId());
                if (!lesson.isPresent()) {
                    logger.error("Test result with lesson id {} not found!", test.getTestConfig().get(0).getLessonId());
                    throw new OnlineLearningException(ErrorTypes.LESSON_NOT_FOUND);
                }
                moduleId = lesson.get().getModuleId();
            }
        } else {
            if (ObjectUtils.isNotEmpty(test.getTestConfig().get(0).getGroupId())) {
                Optional<ContentGroup> contentGroup = contentGroupRepository.findById(test.getTestConfig().get(0).getGroupId());
                if (!contentGroup.isPresent()) {
                    logger.error("Content group not found with content group id {}", test.getTestConfig().get(0).getGroupId());
                    throw new OnlineLearningException(ErrorTypes.CONTENT_GROUP_NOT_FOUND);
                }
                moduleId = contentGroup.get().getTypeId();
            }
        }
        Optional<SubjectSetting> subjectSetting = subjectSettingRepository.findBySubjectSettingId(moduleId);
        if (!subjectSetting.isPresent()) {
            logger.error("Subject setting with id {} not found!", moduleId);
            throw new OnlineLearningException(ErrorTypes.SUBJECT_SETTING_NOT_FOUND, String.valueOf(moduleId));
        }
        testResultReview.setModuleName(subjectSetting.get().getSubjectSettingTitle());
        testResultReview.setConfigType(testAnalyses.get(0).getTestConfigType());
        testResultReview.setQuestionResult(listCorrectAnswer);
        testResultReview.setResultAnalysis(resultAnalysisReviews);
        return testResultReview;
    }

    @Override
    public void disable(Long testId) {
        Optional<Test> test = testRepository.findById(testId);
        if (!test.isPresent()) {
            logger.error("Test not found with test id: {}", testId);
            throw new OnlineLearningException(ErrorTypes.TEST_NOT_FOUND, testId.toString());
        }
        test.get().setStatus(Boolean.FALSE);
        testRepository.save(test.get());
    }

    @Override
    public void enable(Long testId) {
        Optional<Test> test = testRepository.findById(testId);
        if (!test.isPresent()) {
            logger.error("Test not found with test id: {}", testId);
            throw new OnlineLearningException(ErrorTypes.TEST_NOT_FOUND, testId.toString());
        }
        test.get().setStatus(Boolean.TRUE);
        testRepository.save(test.get());
    }

    @Override
    public ResultTestDto getAnswerQuizAndPractice(Long testId, TestRandomResponseDto request, Integer type) {
        Optional<Test> test = testRepository.findById(testId);
        if (!test.isPresent()) {
            logger.error("Test not found with test id: {}", testId);
            throw new OnlineLearningException(ErrorTypes.TEST_NOT_FOUND, testId.toString());
        }
        List<Long> questionIds = request.getQuestions().stream()
                .map(TestQuestionRandomDto::getQuestionId)
                .collect(Collectors.toList());

        TestRandomResponseDto testRandom = getQuestionByIdInAndConvertToTestResponseDto(test.get(), questionIds,
                Test.PRACTICE_TEST);
        List<TestQuestionRandomDto> listCorrectAnswer = testRandom.getQuestions();
        listCorrectAnswer.sort(Comparator.comparing(TestQuestionRandomDto::getQuestionId));
        List<TestQuestionRandomDto> listUserAnswer = request.getQuestions();
        listUserAnswer.sort(Comparator.comparing(TestQuestionRandomDto::getQuestionId));
        int score = 0;
        int numberQuestion = listUserAnswer.size();
        List<TestQuestionRandomDto> listResult = new ArrayList<>();
        ResultTestDto resultTestDto = new ResultTestDto();
        Map<Long, Map<String, Double>> resultAnalysis = new HashMap<>();
        List<Long> lessonResultAnalyses = new ArrayList<>();
        if (request.getConfigType().equalsIgnoreCase(TestConfig.LESSON_CONFIG_TYPE)) {
            for (int i = 0; i < numberQuestion; i++) {
                TestQuestionRandomDto userAnswerResult = compareAnswer(listCorrectAnswer.get(i), listUserAnswer.get(i));
                if (userAnswerResult.getIsCorrect().equals(Boolean.TRUE)) {
                    score++;
                    lessonResultAnalyses.add(listUserAnswer.get(i).getLessonId());
                }
                listResult.add(userAnswerResult);
            }
            List<TestConfigDto> testConfigs = request.getTestConfigs();

            testConfigs.forEach(t -> {
                long correctNumber = lessonResultAnalyses.stream()
                        .filter(l -> l.equals(t.getLessonId()))
                        .count();
                Map<String, Double> nameWithPercent = new HashMap<>();
                nameWithPercent.put(lessonRepository.getNameLessonById(t.getLessonId()),
                        (double) correctNumber / (double) t.getQuantity() * 100);
                resultAnalysis.put(t.getLessonId(), nameWithPercent);
            });
        }
        if (request.getConfigType().equalsIgnoreCase(TestConfig.GROUP_CONFIG_TYPE)) {
            for (int i = 0; i < numberQuestion; i++) {
                TestQuestionRandomDto userAnswerResult = compareAnswer(listCorrectAnswer.get(i), listUserAnswer.get(i));
                if (userAnswerResult.getIsCorrect().equals(Boolean.TRUE)) {
                    score++;
                    lessonResultAnalyses.add(listUserAnswer.get(i).getGroupId());
                }
                listResult.add(userAnswerResult);
            }
            List<TestConfigDto> testConfigs = request.getTestConfigs();

            testConfigs.forEach(t -> {
                long correctNumber = lessonResultAnalyses.stream()
                        .filter(l -> l.equals(t.getGroupId()))
                        .count();
                Map<String, Double> nameWithPercent = new HashMap<>();
                nameWithPercent.put(contentGroupRepository.getNameContentGroupById(t.getGroupId()),
                        (double) correctNumber / (double) t.getQuantity() * 100);
                resultAnalysis.put(t.getGroupId(), nameWithPercent);
            });
        }
        resultTestDto.setResultAnalysis(resultAnalysis);
        double resultPercentage = ((double) score / (double) numberQuestion) * 100;
        resultTestDto.setTotalOfQuestion(numberQuestion);
        resultTestDto.setQuestionResult(listResult);
        resultTestDto.setNumberCorrect(score);
        resultTestDto.setScorePercent(Math.floor(resultPercentage * 100) / 100);
        resultTestDto.setIsPass(checkPassed(resultTestDto.getScorePercent(), test.get().getPassRate()));
        if (ObjectUtils.isNotEmpty(request.getTotalTimeSeconds())) {
            resultTestDto.setTotalTime((double) request.getTotalTimeSeconds() / 60);
            resultTestDto.setSecondPerQuestion((double) request.getTotalTimeSeconds() / (double) numberQuestion);
        } else {
            resultTestDto.setTotalTime(0);
            resultTestDto.setSecondPerQuestion(0);
        }
        saveTestResultForQuizAndPractice(test.get(), resultTestDto, request, type);
        return resultTestDto;
    }

    @Override
    public List<TestFilterDto> getAllTestForQuizLesson(Long subjectId) {
        return testRepository.getAllTestForQuizLesson(subjectId);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void saveTestResultForQuizAndPractice(Test test, ResultTestDto resultTestDto, TestRandomResponseDto testRandom,
                                                 Integer type) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userRepository.getIdByUserName(userDetails.getUsername());

        if (ObjectUtils.isEmpty(userId)) {
            logger.error("User not found with user name: {}", userDetails.getUsername());
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND, userDetails.getUsername());
        }

        Long classLessonId = 0L;
        if (Objects.equals(test.getTestType(), Test.QUIZ_LESSON)) {
            //Study result
            StudyResult studyResult = new StudyResult();
            Optional<StudyResult> existStudyResult;
            switch (type) {
                case Lesson.LESSON_QUIZ_TYPE:
                    existStudyResult
                            = studyResultRepository.findBySubjectIdAndLessonIdAndUserId(test.getSubject().getSubjectId(),
                            test.getSourceId(), userId);
                    if (existStudyResult.isPresent()) {
                        studyResult = existStudyResult.get();
                    }
                    studyResult.setLessonId(test.getSourceId());
                    break;
                case ClassLesson.CLASS_LESSON_QUIZ_TYPE:
                    classLessonId = classLessonRepository.findClassLessonIdByTestId(test.getTestId());
                    existStudyResult
                            = studyResultRepository.findBySubjectIdAndClassLessonIdAndUserId(test.getSubject().getSubjectId(),
                            classLessonId, userId);
                    if (existStudyResult.isPresent()) {
                        studyResult = existStudyResult.get();
                    }
                    studyResult.setClassLessonId(classLessonId);
                    break;
                default:
                    logger.error("Type of quiz must be define!");
                    throw new OnlineLearningException(CommonErrorTypes.INVALID_INPUT);
            }
            studyResult.setUserId(userId);
            studyResult.setSubjectId(test.getSubject().getSubjectId());
            studyResult.setLessonResult(resultTestDto.getScorePercent());
            studyResult.setIsPassed(checkPassed(resultTestDto.getScorePercent(), test.getPassRate()));
            studyResult.setIsCompleted(checkPassed(resultTestDto.getScorePercent(), test.getPassRate()));

            /*
            ---- Set started date for quiz lesson
            Date startedDate = new Date();
            long second = startedDate.getTime() / 1000;
            long startedDateToSecond = second - testRandom.getTotalTimeSeconds();
            studyResult.setStartedDate(new Date(startedDateToSecond * 1000));
             */
            studyResultRepository.save(studyResult);
        }
        //User test
        UserTest userTest = new UserTest();
        Optional<UserTest> existUserTest;
        switch (type) {
            case Lesson.LESSON_QUIZ_TYPE:
                existUserTest = userTestRepository.findByTestAndUserIdAndClassLessonIdIsNull(test, userId);
                if (existUserTest.isPresent()) {
                    userTest = existUserTest.get();
                }
                userTest.setLessonId(test.getSourceId());
                break;
            case ClassLesson.CLASS_LESSON_QUIZ_TYPE:
                existUserTest = userTestRepository.findByTestAndUserIdAndLessonIdIsNull(test, userId);
                if (existUserTest.isPresent()) {
                    userTest = existUserTest.get();
                }
                userTest.setClassLessonId(classLessonId);
                break;
            default:
                logger.error("Type of quiz must be define!");
                throw new OnlineLearningException(CommonErrorTypes.INVALID_INPUT);
        }
        userTest.setTest(test);
        userTest.setStartedDate(new Date());
        userTest.setTotalTime(resultTestDto.getTotalTime());
        userTest.setSecondPerQuestion(resultTestDto.getSecondPerQuestion());
        userTest.setUserId(userId);
        userTest.setCorrects(resultTestDto.getNumberCorrect());
        userTest.setTotal(resultTestDto.getTotalOfQuestion());
        userTest.setScorePercent(resultTestDto.getScorePercent());
        userTest.setIsPassed(resultTestDto.getIsPass());
        userTestRepository.save(userTest);

        //Test Analysis
        UserTest checkUserTest = userTest;
        List<TestAnalysis> existTestAnalyses = testAnalysisRepository.findAllByUserTestId(userTest.getUserTestId());
        if (CollectionUtils.isNotEmpty(existTestAnalyses)) {
            testAnalysisRepository.deleteAll(existTestAnalyses);
        }
        List<TestAnalysis> testAnalyses = new ArrayList<>();
        resultTestDto.getResultAnalysis().forEach((k, v) -> {
            TestAnalysis testAnalysis = new TestAnalysis();
            testAnalysis.setUserTestId(checkUserTest.getUserTestId());
            testAnalysis.setTypeId(k);
            testAnalysis.setTypeName(v.keySet().toString());
            String[] percent = v.entrySet().toString().split("=");
            Pattern pattern = Pattern.compile("[^0-9 .]");
            Matcher matcher = pattern.matcher(percent[1]);
            String number = matcher.replaceAll("");
            testAnalysis.setPercent(ObjectUtils.isEmpty(percent[1]) ? 0d : Double.parseDouble(number));
            if (testRandom.getConfigType().equalsIgnoreCase(TestConfig.GROUP_CONFIG_TYPE)) {
                testAnalysis.setTestConfigType(TestConfig.GROUP_CONFIG_TYPE);
            }
            if (testRandom.getConfigType().equalsIgnoreCase(TestConfig.LESSON_CONFIG_TYPE)) {
                testAnalysis.setTestConfigType(TestConfig.LESSON_CONFIG_TYPE);
            }
            testAnalyses.add(testAnalysis);
            testAnalysisRepository.saveAll(testAnalyses);
        });

        //User answer
        List<UserAnswer> existUserAnswers = userAnswerRepository.findAllByUserTest(userTest);
        if (CollectionUtils.isNotEmpty(existUserAnswers)) {
            userAnswerRepository.deleteAll(existUserAnswers);
        }
        List<UserAnswer> userAnswers = new ArrayList<>();
        testRandom.getQuestions().forEach(q -> {
            UserAnswer userAnswer = new UserAnswer();
            userAnswer.setUserAnswerId(new UserAnswerId(checkUserTest.getUserTestId(), q.getQuestionId()));
            userAnswer.setIsCorrect(q.getIsCorrect());
            userAnswer.setMarked(q.getMarked());
            userAnswer.setGroupId(q.getGroupId());
            StringBuilder answer = new StringBuilder();
            for (int i = 0; i < q.getAnswerOptions().size(); i++) {
                if (ObjectUtils.isNotEmpty(q.getAnswerOptions().get(i).getUserAnswer())) {
                    if (i == q.getAnswerOptions().size() - 1) {
                        answer.append(q.getAnswerOptions().get(i).getId().toString());
                    } else {
                        answer.append(q.getAnswerOptions().get(i).getId()).append(",");
                    }
                }
            }
            userAnswer.setAnswers(answer.toString());
            userAnswer.setUserTest(checkUserTest);
            userAnswer.setQuestion(BeanUtility.convertValue(q, Question.class));
            userAnswers.add(userAnswer);
        });
        userAnswerRepository.saveAll(userAnswers);
    }

    private TestResponseDto mapTestDto(Test test) {
        TestResponseDto testResponse = new TestResponseDto();
        testResponse.setTestId(test.getTestId());
        testResponse.setName(test.getName());
        testResponse.setDuration(test.getDuration());
        testResponse.setPassRate(test.getPassRate());
        testResponse.setStatus(test.getStatus());
        testResponse.setTestType(test.getTestType());
        testResponse.setSubjectId(test.getSubject().getSubjectId());
        if (checkTestType(test.getTestType())) {
            testResponse.setNumberOfQuestion(countTotalQuestionInTestAnotherType(test));
        } else {
            testResponse.setNumberOfQuestion(countTotalQuestionInTestQuizLessonAndPracticeTest(test));
        }
        if (CollectionUtils.isNotEmpty(test.getTestConfig())) {
            testResponse.setTestConfigs(BeanUtility.mapList(test.getTestConfig(), TestConfig.class));
        }
        return testResponse;
    }

    private Boolean checkTestType(Integer testType) {
        return Objects.equals(testType, Test.DEMO_TEST) || Objects.equals(testType, Test.FULL_TEST)
                || Objects.equals(testType, Test.MOCK_TEST);
    }

    private TestHistoryDto mapTestHistory(Test test) {
        TestHistoryDto testHistory = new TestHistoryDto();
        testHistory.setTestId(test.getTestId());
        testHistory.setName(test.getName());
        testHistory.setTestType(test.getTestType());
        testHistory.setDuration(test.getDuration());
        testHistory.setPassRate(test.getPassRate());
        testHistory.setStatus(test.getStatus());
        return testHistory;
    }

    private Boolean checkPassed(Double result, Integer passRate) {
        return result >= (double) passRate;
    }

    private TestQuestionRandomDto convertQuestionToDto(Question question, Long groupId, boolean hasKey) {
        TestQuestionRandomDto questionResponseDto = new TestQuestionRandomDto();
        questionResponseDto.setQuestionId(question.getQuestionId());
        questionResponseDto.setBody(question.getBody());
        questionResponseDto.setImageUrl(question.getImageUrl());
        questionResponseDto.setExplanation(question.getExplanation());
        questionResponseDto.setSource(question.getSource());
        questionResponseDto.setPage(question.getPage());
        questionResponseDto.setLessonId(question.getLessonId());
        questionResponseDto.setGroupId(groupId);
        questionResponseDto.setLessonName(lessonRepository.getNameLessonById(question.getLessonId()));
        StringBuilder domain = new StringBuilder();
        if (CollectionUtils.isNotEmpty(question.getContentGroups())) {
            question.getContentGroups().forEach(c -> {
                domain.append(c.getName());
                domain.append(" | ");
            });
            questionResponseDto.setDomain(domain.substring(0, domain.length() - 3));
        }
        List<AnswerOption> answerOptions = question.getAnswerOptions();
        if (!hasKey) answerOptions.forEach(a -> a.setIsKey(null));
        questionResponseDto.setAnswerOptions(BeanUtility.mapList(answerOptions, AnswerOptionResponseDto.class));
        return questionResponseDto;
    }

    private TestQuestionRandomDto compareAnswer(TestQuestionRandomDto correctAnswer, TestQuestionRandomDto userAnswer) {
        if (!correctAnswer.getQuestionId().equals(userAnswer.getQuestionId())) {
            return userAnswer;
        }
        List<AnswerOptionResponseDto> correctAnswerOption = correctAnswer.getAnswerOptions();
        List<AnswerOptionResponseDto> userAnswerOption = userAnswer.getAnswerOptions();
        correctAnswerOption.sort(Comparator.comparing(AnswerOptionResponseDto::getId));
        userAnswerOption.sort(Comparator.comparing(AnswerOptionResponseDto::getId));
        boolean check = true;
        for (int i = 0; i < correctAnswerOption.size(); i++) {
            if (ObjectUtils.isEmpty(correctAnswerOption.get(i).getIsKey())) {
                logger.info("Invalid correct answer, empty is key");
                throw new OnlineLearningException(CommonErrorTypes.INVALID_INPUT);
            }
            if (!correctAnswerOption.get(i).getIsKey().equals(false)
                    && ObjectUtils.isEmpty(userAnswerOption.get(i).getUserAnswer())) {
                check = false;
            }
            if (!correctAnswerOption.get(i).getIsKey().equals(userAnswerOption.get(i).getUserAnswer())
                    && ObjectUtils.isNotEmpty(userAnswerOption.get(i).getUserAnswer())) {
                check = false;
            }
        }
        userAnswer.setIsCorrect(check);
        return userAnswer;
    }

    private TestRandomResponseDto getQuestionByIdInAndConvertToTestResponseDto(Test test, List<Long> questionIds,
                                                                               Integer testType) {
        List<Question> questionList = questionRepository.findQuestionByQuestionIdIn(questionIds);

        //Mapping Test response
        TestRandomResponseDto testRandomResponse = new TestRandomResponseDto();
        testRandomResponse.setTestId(test.getTestId());
        testRandomResponse.setTestType(test.getTestType());
        testRandomResponse.setDuration(test.getDuration());
        testRandomResponse.setName(test.getName());
        testRandomResponse.setPassRate(test.getPassRate());
        testRandomResponse.setConfigType(TestConfig.LESSON_CONFIG_TYPE);
        testRandomResponse.setTestConfigs(BeanUtility.mapList(test.getTestConfig(), TestConfigDto.class));

        //Convert list question to Question response dto
        List<TestQuestionRandomDto> questionResponses = new ArrayList<>();
        questionList.forEach(q -> {
            if (Objects.equals(testType, Test.QUIZ_LESSON)) {
                questionResponses.add(convertQuestionToDto(q, null, Boolean.FALSE));
            }
            if (Objects.equals(testType, Test.PRACTICE_TEST)) {
                questionResponses.add(convertQuestionToDto(q, null, Boolean.TRUE));
            }
        });
        testRandomResponse.setQuestions(questionResponses);
        return testRandomResponse;
    }

    private TestRandomResponseDto getQuestionAndConvertToTestResponseCaseContentGroup(Test test,
                                                                                      List<QuestionContentQueryDto> questionContent,
                                                                                      Integer testType) {
        List<Long> questionIds = questionContent.stream()
                .map(QuestionContentQueryDto::getQuestionId)
                .collect(Collectors.toList());
        List<Question> questionList = questionRepository.findQuestionByQuestionIdIn(questionIds);

        if (questionIds.size() != questionList.size()) {
            Set<Long> items = new HashSet<>();
            List<Long> duplicateId = questionIds.stream()
                    .filter(q -> !items.add(q))
                    .collect(Collectors.toList());
            List<Question> duplicateQuestion = questionRepository.findQuestionByQuestionIdIn(duplicateId);
            duplicateQuestion.forEach(d -> {
                questionList.add(d);
            });
        }
        //Mapping Test response
        TestRandomResponseDto testRandomResponse = new TestRandomResponseDto();
        testRandomResponse.setTestId(test.getTestId());
        testRandomResponse.setTestType(test.getTestType());
        testRandomResponse.setDuration(test.getDuration());
        testRandomResponse.setName(test.getName());
        testRandomResponse.setPassRate(test.getPassRate());
        testRandomResponse.setConfigType(TestConfig.GROUP_CONFIG_TYPE);
        testRandomResponse.setTestConfigs(BeanUtility.mapList(test.getTestConfig(), TestConfigDto.class));

        //Convert list question to Question response dto
        List<TestQuestionRandomDto> questionResponses = new ArrayList<>();
        questionList.forEach(q -> {
            AtomicReference<Long> contentGroupId = new AtomicReference<>();
            questionContent.forEach(qc -> {
                if (Objects.equals(q.getQuestionId(), qc.getQuestionId())) {
                    contentGroupId.set(qc.getGroupId());
                }
            });
            if (Objects.equals(testType, Test.QUIZ_LESSON)) {
                questionResponses.add(convertQuestionToDto(q, contentGroupId.get(), Boolean.FALSE));
            }
            if (Objects.equals(testType, Test.PRACTICE_TEST)) {
                questionResponses.add(convertQuestionToDto(q, contentGroupId.get(), Boolean.TRUE));
            }
        });
        testRandomResponse.setQuestions(questionResponses);
        return testRandomResponse;
    }

    private List<ContentGroupTotal> getContentGroupConfig(List<TestConfig> testConfigs) {
        List<ContentGroupTotal> contentGroupTotals = new ArrayList<>();
        testConfigs.forEach(t -> {
            if (ObjectUtils.isNotEmpty(t.getGroupId())) {
                contentGroupTotals.add(new ContentGroupTotal(t.getGroupId(), (long) t.getQuantity()));
            }
        });
        return contentGroupTotals;
    }

    //Get List Test config following test id and total question of each lesson (by lesson in test config)
    private PrePareLessonConfigDto getPrepareLessonConfig(Test test) {
        Long subjectId = test.getSubject().getSubjectId();
        List<TestConfig> testConfigs = test.getTestConfig();

        PrePareLessonConfigDto prePareLessonConfigDto = new PrePareLessonConfigDto();
        prePareLessonConfigDto.setSubjectId(subjectId);
        if (CollectionUtils.isEmpty(testConfigs)) {
            logger.error("Config this test is empty with test id: {}", test.getTestId());
        }
        if (ObjectUtils.isNotEmpty(testConfigs.get(0).getLessonId())) {
            List<QuizLessonTotal> lessonTotals = getLessonConfig(testConfigs);
            prePareLessonConfigDto.setLessonTotals(lessonTotals);
        } else {
            List<ContentGroupTotal> contentGroupTotals = getContentGroupConfig(testConfigs);
            prePareLessonConfigDto.setContentGroupTotals(contentGroupTotals);
        }
        return prePareLessonConfigDto;
    }

    private List<QuizLessonTotal> getLessonConfig(List<TestConfig> testConfigs) {
        List<QuizLessonTotal> lessonTotals = new ArrayList<>();
        testConfigs.forEach(t -> {
            if (ObjectUtils.isNotEmpty(t.getLessonId())) {
                lessonTotals.add(new QuizLessonTotal(t.getLessonId(), (long) t.getQuantity()));
            }
        });
        return lessonTotals;
    }

    private void saveUserAnswerForSimulationAndDemo(UserTest userTest, TestRandomResponseDto testRandom) {
        List<UserAnswer> existUserAnswers = userAnswerRepository.findAllByUserTest(userTest);
        if (CollectionUtils.isNotEmpty(existUserAnswers)) {
            userAnswerRepository.deleteAll(existUserAnswers);
        }
        UserTest checkUserTest = userTest;
        List<UserAnswer> userAnswers = new ArrayList<>();
        testRandom.getQuestions().forEach(q -> {
            UserAnswer userAnswer = new UserAnswer();
            userAnswer.setUserAnswerId(new UserAnswerId(checkUserTest.getUserTestId(), q.getQuestionId()));
            userAnswer.setIsCorrect(q.getIsCorrect());
            StringBuilder answer = new StringBuilder();
            for (int i = 0; i < q.getAnswerOptions().size(); i++) {
                if (ObjectUtils.isNotEmpty(q.getAnswerOptions().get(i).getUserAnswer())) {
                    if (i == q.getAnswerOptions().size() - 1) {
                        answer.append(q.getAnswerOptions().get(i).getId().toString());
                    } else {
                        answer.append(q.getAnswerOptions().get(i).getId()).append(",");
                    }
                }
            }
            userAnswer.setAnswers(answer.toString());
            userAnswer.setUserTest(checkUserTest);
            userAnswer.setQuestion(BeanUtility.convertValue(q, Question.class));
            userAnswers.add(userAnswer);
        });
        userAnswerRepository.saveAll(userAnswers);
    }
}
