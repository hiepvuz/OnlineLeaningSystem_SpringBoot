//package swp490.g7.OnlineLearningSystem.service.test;
//
//import org.apache.commons.lang3.ObjectUtils;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import swp490.g7.OnlineLearningSystem.entities.content_group.domain.ContentGroup;
//import swp490.g7.OnlineLearningSystem.entities.content_group.repository.ContentGroupRepository;
//import swp490.g7.OnlineLearningSystem.entities.lesson.domain.Lesson;
//import swp490.g7.OnlineLearningSystem.entities.lesson.repository.LessonRepository;
//import swp490.g7.OnlineLearningSystem.entities.question.domain.AnswerOption;
//import swp490.g7.OnlineLearningSystem.entities.question.domain.Question;
//import swp490.g7.OnlineLearningSystem.entities.question.repository.QuestionRepository;
//import swp490.g7.OnlineLearningSystem.entities.study_result.repository.StudyResultRepository;
//import swp490.g7.OnlineLearningSystem.entities.subject.domain.Subject;
//import swp490.g7.OnlineLearningSystem.entities.subject.repository.SubjectRepository;
//import swp490.g7.OnlineLearningSystem.entities.subject_setting.domain.SubjectSetting;
//import swp490.g7.OnlineLearningSystem.entities.subject_setting.repository.SubjectSettingRepository;
//import swp490.g7.OnlineLearningSystem.entities.test.domain.Test;
//import swp490.g7.OnlineLearningSystem.entities.test.domain.TestConfig;
//import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.TestResultReview;
//import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.TestRandomResponseDto;
//import swp490.g7.OnlineLearningSystem.entities.test.repository.TestRepository;
//import swp490.g7.OnlineLearningSystem.entities.test.service.impl.TestServiceImpl;
//import swp490.g7.OnlineLearningSystem.entities.user.domain.User;
//import swp490.g7.OnlineLearningSystem.entities.user.repository.UserRepository;
//import swp490.g7.OnlineLearningSystem.entities.user_test.domain.UserTest;
//import swp490.g7.OnlineLearningSystem.entities.user_test.repository.TestAnalysisRepository;
//import swp490.g7.OnlineLearningSystem.entities.user_test.repository.UserAnswerRepository;
//import swp490.g7.OnlineLearningSystem.entities.user_test.repository.UserTestRepository;
//import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Optional;
//
//@ExtendWith(MockitoExtension.class)
//public class TestServiceTest {
//    @Mock
//    private TestRepository testRepository;
//    @Mock
//    private QuestionRepository questionRepository;
//    @Mock
//    private UserTestRepository userTestRepository;
//    @Mock
//    private UserRepository userRepository;
//    @Mock
//    private ContentGroupRepository contentGroupRepository;
//    @Mock
//    private LessonRepository lessonRepository;
//    @Mock
//    private SubjectSettingRepository subjectSettingRepository;
//
//    @InjectMocks
//    private TestServiceImpl testService;
//
//    private static List<Subject> subjects;
//    private static List<Test> tests;
//    private static List<User> users;
//    private static List<Question> questions;
//    private static List<UserTest> userTests;
//    private static List<Lesson> lessons;
//    private static ContentGroup contentGroup;
//    private static SubjectSetting subjectSetting;
//
//    @BeforeEach
//    private void setup() {
//        subjects = buildSubjects();
//        tests = buildTests();
//        users = buildUser();
//        questions = buildQuestion();
//        userTests = buildUserTest();
//        lessons = buildLesson();
//        contentGroup = contentGroup;
//        subjectSetting = subjectSetting;
//    }
//
//    private static SubjectSetting buildSubjectSetting() {
//        SubjectSetting subjectSetting = new SubjectSetting();
//        subjectSetting.setSubjectId(1L);
//        subjectSetting.setSubjectSettingTitle("Java Syntax");
//        subjectSetting.setTypeId(3L);
//        return subjectSetting;
//    }
//
//    private static ContentGroup buildContentGroup() {
//        ContentGroup contentGroup = new ContentGroup();
//        contentGroup.setName("Java Comments");
//        contentGroup.setGroupId(1L);
//        contentGroup.setTypeId(3L);
//        return contentGroup;
//    }
//
//    private static List<Lesson> buildLesson() {
//        Lesson lesson = new Lesson();
//        lesson.setLessonId(1L);
//        lesson.setTypeLesson(Lesson.LESSON_TYPE_QUIZ);
//        lesson.setSubject(subjects.get(0));
//        lesson.setStatus(Boolean.TRUE);
//        lesson.setTestId(tests.get(0).getTestId());
//
//        Lesson lesson1 = new Lesson();
//        lesson1.setLessonId(2L);
//        lesson1.setTypeLesson(Lesson.LESSON_TYPE_QUIZ);
//        lesson1.setSubject(subjects.get(0));
//        lesson1.setStatus(Boolean.TRUE);
//        lesson.setTestId(tests.get(0).getTestId());
//
//        List<Lesson> lessons = new ArrayList<>();
//        lessons.add(lesson);
//        lessons.add(lesson1);
//        return lessons;
//    }
//
//    private static List<Subject> buildSubjects() {
//        Subject subject = new Subject();
//        subject.setSubjectId(1L);
//        subject.setBody("Java core");
//        subject.setExpertId(1L);
//        subject.setManagerId(2L);
//        subject.setStatus(Boolean.TRUE);
//        subject.setSubjectCode("PRJ101");
//        subject.setSubjectName("Java");
//        List<Subject> subjects = new ArrayList<>();
//        subjects.add(subject);
//        return subjects;
//    }
//
//    private static List<Test> buildTests() {
//        Test test = new Test();
//        test.setTestId(1L);
//        test.setName("This test for demo");
//        test.setStatus(Boolean.TRUE);
//        test.setTestType(1);
//        test.setSubject(buildSubjects().get(0));
//        test.setPassRate(80);
//        test.setDuration(50);
//        test.setDescription("This test for demo with subject Java core");
//        List<TestConfig> testConfigs = new ArrayList<>();
//        TestConfig testConfig = new TestConfig();
//        testConfig.setTest(test);
//        testConfig.setLessonId(1L);
//        testConfig.setGroupId(1L);
//        testConfig.setId(1L);
//        testConfig.setQuantity(5);
//        testConfigs.add(testConfig);
//        test.setTestConfig(testConfigs);
//        Test test1 = new Test();
//        test1.setTestId(2L);
//        test1.setName("This test for full test");
//        test1.setStatus(Boolean.TRUE);
//        test1.setTestType(3);
//        test1.setSubject(buildSubjects().get(0));
//        test1.setPassRate(80);
//        test1.setDuration(50);
//        test1.setDescription("This full test with subject Java core");
//        List<TestConfig> testConfig1s = new ArrayList<>();
//        TestConfig testConfig1 = new TestConfig();
//        testConfig1.setTest(test);
//        testConfig1.setLessonId(1L);
//        testConfig1.setGroupId(1L);
//        testConfig1.setId(1L);
//        testConfig1.setQuantity(5);
//        testConfig1s.add(testConfig1);
//        test1.setTestConfig(testConfig1s);
//        List<Test> tests = new ArrayList<>();
//        tests.add(test);
//        tests.add(test1);
//        return tests;
//    }
//
//    private static List<User> buildUser() {
//        User user1 = new User();
//        user1.setUserId(1L);
//        user1.setDisabled(Boolean.FALSE);
//        user1.setUsername("admin");
//        user1.setEmail("admin@gmail.com");
//        user1.setVerify(Boolean.TRUE);
//        user1.setPassword("123");
//        User user2 = new User();
//        user2.setUserId(2L);
//        user2.setDisabled(Boolean.FALSE);
//        user2.setUsername("trainee");
//        user2.setEmail("trainee@gmail.com");
//        user2.setVerify(Boolean.TRUE);
//        user2.setPassword("123");
//        List<User> users = new ArrayList<>();
//        users.add(user1);
//        users.add(user2);
//        return users;
//    }
//
//    private static List<Question> buildQuestion() {
//        Question question1 = new Question();
//        question1.setQuestionId(1L);
//        question1.setBody("This is first question?");
//        question1.setTestId(buildTests().get(0).getTestId());
//        question1.setSubject(buildSubjects().get(0));
//        question1.setLessonId(1L);
//        AnswerOption answerOption1 = new AnswerOption();
//        answerOption1.setId(1L);
//        answerOption1.setQuestion(question1);
//        answerOption1.setAnswerText("True");
//        answerOption1.setIsKey(Boolean.TRUE);
//        AnswerOption answerOption2 = new AnswerOption();
//        answerOption2.setId(2L);
//        answerOption2.setQuestion(question1);
//        answerOption2.setAnswerText("False");
//        answerOption2.setIsKey(Boolean.TRUE);
//        List<AnswerOption> answerOptions = new ArrayList<>();
//        answerOptions.add(answerOption1);
//        answerOptions.add(answerOption2);
//        question1.setAnswerOptions(answerOptions);
//        Question question2 = new Question();
//        question2.setQuestionId(2L);
//        question2.setBody("This is first question?");
//        question2.setTestId(buildTests().get(0).getTestId());
//        question2.setSubject(buildSubjects().get(0));
//        question2.setLessonId(1L);
//        AnswerOption answerOption3 = new AnswerOption();
//        answerOption3.setId(3L);
//        answerOption3.setQuestion(question2);
//        answerOption3.setAnswerText("True");
//        answerOption3.setIsKey(Boolean.TRUE);
//        AnswerOption answerOption4 = new AnswerOption();
//        answerOption4.setId(4L);
//        answerOption4.setQuestion(question2);
//        answerOption4.setAnswerText("False");
//        answerOption4.setIsKey(Boolean.TRUE);
//        List<AnswerOption> answerOptions1 = new ArrayList<>();
//        answerOptions1.add(answerOption4);
//        answerOptions1.add(answerOption4);
//        question2.setAnswerOptions(answerOptions1);
//        List<Question> questions = new ArrayList<>();
//        questions.add(question1);
//        questions.add(question2);
//        return questions;
//    }
//
//    private static List<UserTest> buildUserTest() {
//        UserTest userTest = new UserTest();
//        userTest.setTest(tests.get(0));
//        userTest.setCorrects(20);
//        userTest.setLessonId(1L);
//        userTest.setUserId(users.get(0).getUserId());
//        userTest.setTotal(20);
//        userTest.setSecondPerQuestion(60.0);
//        userTest.setTotal(20);
//        userTest.setScorePercent(100.0);
//        userTest.setIsPassed(Boolean.TRUE);
//        UserTest userTest1 = new UserTest();
//        userTest1.setTest(tests.get(1));
//        userTest1.setCorrects(10);
//        userTest1.setLessonId(1L);
//        userTest1.setUserId(users.get(1).getUserId());
//        userTest1.setTotal(20);
//        userTest1.setSecondPerQuestion(60.0);
//        userTest1.setTotal(20);
//        userTest1.setScorePercent(50.0);
//        userTest1.setIsPassed(Boolean.FALSE);
//        List<UserTest> userTests = new ArrayList<>();
//        userTests.add(userTest);
//        userTests.add(userTest1);
//        return userTests;
//    }
//
//    @org.junit.jupiter.api.Test
//    void testDemo_success() {
//        Mockito.lenient().when(testRepository.findById(1L)).thenReturn(Optional.of(tests.get(0)));
//        Mockito.lenient().when(questionRepository.findByTestIdAndSubject(1L, tests.get(0).getSubject())).thenReturn(questions);
//        Mockito.lenient().when(lessonRepository.getNameLessonById(1L)).thenReturn("LessonA");
//        Test test = tests.get(0);
//        Mockito.lenient().when(lessonRepository.findById(test.getTestConfig().get(0).getLessonId()))
//                .thenReturn(Optional.of(lessons.get(0)));
//        Mockito.lenient().when(contentGroupRepository.findById(test.getTestConfig().get(0).getGroupId()))
//                        .thenReturn(Optional.of(contentGroup));
//        Mockito.lenient().when(subjectSettingRepository.findBySubjectSettingId(1L)).thenReturn(Optional.of(subjectSetting));
//        Mockito.lenient().when(contentGroupRepository.getNameContentGroupById(1L)).thenReturn("ContentA");
//        TestRandomResponseDto testRandomResponseDto = testService.getSimulationAndDemo(1L);
//        Assertions.assertEquals(testRandomResponseDto.getTestId(), 1L);
//        Assertions.assertEquals(testRandomResponseDto.getQuestions().size(), 2);
//    }
//
//    @org.junit.jupiter.api.Test
//    void testDemo_shouldThrowTestNotFound() {
//        Mockito.lenient().when(testRepository.findById(1L)).thenReturn(Optional.empty());
//        Mockito.lenient().when(questionRepository.findByTestIdAndSubject(1L, tests.get(0).getSubject())).thenReturn(questions);
//        Mockito.lenient().when(lessonRepository.getNameLessonById(1L)).thenReturn("LessonA");
//        Mockito.lenient().when(contentGroupRepository.getNameContentGroupById(1L)).thenReturn("ContentA");
//        Assertions.assertThrows(OnlineLearningException.class, () -> {
//            testService.getSimulationAndDemo(1L);
//        });
//    }
//
//    @org.junit.jupiter.api.Test
//    void testDemo_shouldThrowQuestionNotFound() {
//        Mockito.lenient().when(testRepository.findById(1L)).thenReturn(Optional.empty());
//        Mockito.lenient().when(questionRepository.findByTestIdAndSubject(1L, tests.get(0).getSubject())).thenReturn(null);
//        Mockito.lenient().when(lessonRepository.getNameLessonById(1L)).thenReturn("LessonA");
//        Mockito.lenient().when(contentGroupRepository.getNameContentGroupById(1L)).thenReturn("ContentA");
//        Assertions.assertThrows(OnlineLearningException.class, () -> {
//            testService.getSimulationAndDemo(1L);
//        });
//    }
//
//    @org.junit.jupiter.api.Test
//    void testDemo_shouldNameLessonIsNull() {
//        Mockito.lenient().when(testRepository.findById(1L)).thenReturn(Optional.of(tests.get(0)));
//        Mockito.lenient().when(questionRepository.findByTestIdAndSubject(1L, tests.get(0).getSubject())).thenReturn(questions);
//        Mockito.lenient().when(lessonRepository.findById(tests.get(0).getTestConfig().get(0).getLessonId()))
//                .thenReturn(Optional.of(lessons.get(0)));
//        Mockito.lenient().when(contentGroupRepository.findById(tests.get(0).getTestConfig().get(0).getGroupId()))
//                .thenReturn(Optional.of(contentGroup));
//        Mockito.lenient().when(subjectSettingRepository.findBySubjectSettingId(1L)).thenReturn(Optional.of(subjectSetting));
//        Mockito.lenient().when(lessonRepository.getNameLessonById(1L)).thenReturn(null);
//        Mockito.lenient().when(contentGroupRepository.getNameContentGroupById(1L)).thenReturn("ContentA");
//        TestRandomResponseDto testRandomResponseDto = testService.getSimulationAndDemo(1L);
//        Assertions.assertTrue(ObjectUtils.isEmpty(testRandomResponseDto.getQuestions().get(0).getLessonName()));
//    }
//
//    @org.junit.jupiter.api.Test
//    void testDemo_shouldNameContentGroupIsNull() {
//        Mockito.lenient().when(testRepository.findById(1L)).thenReturn(Optional.of(tests.get(0)));
//        Mockito.lenient().when(questionRepository.findByTestIdAndSubject(1L, tests.get(0).getSubject())).thenReturn(questions);
//        Mockito.lenient().when(lessonRepository.findById(tests.get(0).getTestConfig().get(0).getLessonId()))
//                .thenReturn(Optional.of(lessons.get(0)));
//        Mockito.lenient().when(contentGroupRepository.findById(tests.get(0).getTestConfig().get(0).getGroupId()))
//                .thenReturn(Optional.of(contentGroup));
//        Mockito.lenient().when(subjectSettingRepository.findBySubjectSettingId(1L)).thenReturn(Optional.of(subjectSetting));
//        Mockito.lenient().when(lessonRepository.getNameLessonById(1L)).thenReturn("LessonA");
//        Mockito.lenient().when(contentGroupRepository.getNameContentGroupById(1L)).thenReturn(null);
//        TestRandomResponseDto testRandomResponseDto = testService.getSimulationAndDemo(1L);
//        Assertions.assertEquals(testRandomResponseDto.getTestId(), 1L);
//        Assertions.assertTrue(ObjectUtils.isEmpty(testRandomResponseDto.getQuestions().get(0).getDomain()));
//    }
//
//    @org.junit.jupiter.api.Test
//    void testResultTest_success() {
//        Authentication authentication = Mockito.mock(Authentication.class);
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        UserDetails userDetails = new UserDetails() {
//            @Override
//            public Collection<? extends GrantedAuthority> getAuthorities() {
//                return null;
//            }
//
//            @Override
//            public String getPassword() {
//                return null;
//            }
//
//            @Override
//            public String getUsername() {
//                return "admin";
//            }
//
//            @Override
//            public boolean isAccountNonExpired() {
//                return false;
//            }
//
//            @Override
//            public boolean isAccountNonLocked() {
//                return false;
//            }
//
//            @Override
//            public boolean isCredentialsNonExpired() {
//                return false;
//            }
//
//            @Override
//            public boolean isEnabled() {
//                return false;
//            }
//        };
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
//        Mockito.lenient().when(userRepository.getIdByUserName(userDetails.getUsername())).thenReturn(1L);
//        Mockito.lenient().when(testRepository.findById(1L)).thenReturn(Optional.of(tests.get(0)));
//        Mockito.lenient().when(userTestRepository.findByTestAndUserId(tests.get(0), 1L))
//                .thenReturn(Optional.of(userTests.get(0)));
//        TestResultReview testResultReview = testService.getResultTest(tests.get(0).getTestId());
//        Assertions.assertTrue(ObjectUtils.isNotEmpty(testResultReview));
//        Assertions.assertTrue(testResultReview.getIsPass());
//    }
//
//    @org.junit.jupiter.api.Test
//    void testResultTest_successWithNotPass() {
//        Authentication authentication = Mockito.mock(Authentication.class);
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        UserDetails userDetails = new UserDetails() {
//            @Override
//            public Collection<? extends GrantedAuthority> getAuthorities() {
//                return null;
//            }
//
//            @Override
//            public String getPassword() {
//                return null;
//            }
//
//            @Override
//            public String getUsername() {
//                return "admin";
//            }
//
//            @Override
//            public boolean isAccountNonExpired() {
//                return false;
//            }
//
//            @Override
//            public boolean isAccountNonLocked() {
//                return false;
//            }
//
//            @Override
//            public boolean isCredentialsNonExpired() {
//                return false;
//            }
//
//            @Override
//            public boolean isEnabled() {
//                return false;
//            }
//        };
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
//        Mockito.lenient().when(userRepository.getIdByUserName(userDetails.getUsername())).thenReturn(2L);
//        Mockito.lenient().when(testRepository.findById(2L)).thenReturn(Optional.of(tests.get(1)));
//        Mockito.lenient().when(userTestRepository.findByTestAndUserId(tests.get(1), 2L))
//                .thenReturn(Optional.of(userTests.get(1)));
//        TestResultReview testResultReview = testService.getResultTest(tests.get(1).getTestId());
//        Assertions.assertTrue(ObjectUtils.isNotEmpty(testResultReview));
//        Assertions.assertFalse(testResultReview.getIsPass());
//    }
//
//    @org.junit.jupiter.api.Test
//    void testResultTest_throwUserNotFound() {
//        Authentication authentication = Mockito.mock(Authentication.class);
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        UserDetails userDetails = new UserDetails() {
//            @Override
//            public Collection<? extends GrantedAuthority> getAuthorities() {
//                return null;
//            }
//
//            @Override
//            public String getPassword() {
//                return null;
//            }
//
//            @Override
//            public String getUsername() {
//                return "admin";
//            }
//
//            @Override
//            public boolean isAccountNonExpired() {
//                return false;
//            }
//
//            @Override
//            public boolean isAccountNonLocked() {
//                return false;
//            }
//
//            @Override
//            public boolean isCredentialsNonExpired() {
//                return false;
//            }
//
//            @Override
//            public boolean isEnabled() {
//                return false;
//            }
//        };
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
//        Mockito.lenient().when(userRepository.getIdByUserName(userDetails.getUsername())).thenReturn(null);
//        Mockito.lenient().when(testRepository.findById(2L)).thenReturn(Optional.of(tests.get(1)));
//        Mockito.lenient().when(userTestRepository.findByTestAndUserId(tests.get(1), 2L))
//                .thenReturn(Optional.of(userTests.get(1)));
//        Assertions.assertThrows(OnlineLearningException.class, () -> {
//            testService.getResultTest(tests.get(1).getTestId());
//        });
//    }
//
//    @org.junit.jupiter.api.Test
//    void testResultTest_throwTestNotFound() {
//        Authentication authentication = Mockito.mock(Authentication.class);
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        UserDetails userDetails = new UserDetails() {
//            @Override
//            public Collection<? extends GrantedAuthority> getAuthorities() {
//                return null;
//            }
//
//            @Override
//            public String getPassword() {
//                return null;
//            }
//
//            @Override
//            public String getUsername() {
//                return "admin";
//            }
//
//            @Override
//            public boolean isAccountNonExpired() {
//                return false;
//            }
//
//            @Override
//            public boolean isAccountNonLocked() {
//                return false;
//            }
//
//            @Override
//            public boolean isCredentialsNonExpired() {
//                return false;
//            }
//
//            @Override
//            public boolean isEnabled() {
//                return false;
//            }
//        };
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
//        Mockito.lenient().when(userRepository.getIdByUserName(userDetails.getUsername())).thenReturn(2L);
//        Mockito.lenient().when(testRepository.findById(2L)).thenReturn(Optional.empty());
//        Mockito.lenient().when(userTestRepository.findByTestAndUserId(tests.get(1), 2L))
//                .thenReturn(Optional.of(userTests.get(1)));
//        Assertions.assertThrows(OnlineLearningException.class, () -> {
//            testService.getResultTest(tests.get(1).getTestId());
//        });
//    }
//
//    @org.junit.jupiter.api.Test
//    void testResultTest_throwDataTestUnAvailable() {
//        Authentication authentication = Mockito.mock(Authentication.class);
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        UserDetails userDetails = new UserDetails() {
//            @Override
//            public Collection<? extends GrantedAuthority> getAuthorities() {
//                return null;
//            }
//
//            @Override
//            public String getPassword() {
//                return null;
//            }
//
//            @Override
//            public String getUsername() {
//                return "admin";
//            }
//
//            @Override
//            public boolean isAccountNonExpired() {
//                return false;
//            }
//
//            @Override
//            public boolean isAccountNonLocked() {
//                return false;
//            }
//
//            @Override
//            public boolean isCredentialsNonExpired() {
//                return false;
//            }
//
//            @Override
//            public boolean isEnabled() {
//                return false;
//            }
//        };
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
//        Mockito.lenient().when(userRepository.getIdByUserName(userDetails.getUsername())).thenReturn(2L);
//        Mockito.lenient().when(testRepository.findById(2L)).thenReturn(Optional.of(tests.get(1)));
//        Mockito.lenient().when(userTestRepository.findByTestAndUserId(tests.get(1), 2L))
//                .thenReturn(Optional.empty());
//        Assertions.assertThrows(OnlineLearningException.class, () -> {
//            testService.getResultTest(tests.get(1).getTestId());
//        });
//    }
//}