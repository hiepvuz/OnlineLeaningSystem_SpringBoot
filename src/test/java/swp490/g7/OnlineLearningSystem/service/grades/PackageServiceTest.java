//package swp490.g7.OnlineLearningSystem.service.grades;
//
//import org.apache.commons.collections4.CollectionUtils;
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
//import swp490.g7.OnlineLearningSystem.entities.lesson.domain.Lesson;
//import swp490.g7.OnlineLearningSystem.entities.lesson.repository.LessonRepository;
//import swp490.g7.OnlineLearningSystem.entities.packages.domain.dto.GradesDto;
//import swp490.g7.OnlineLearningSystem.entities.packages.service.impl.PackServiceImpl;
//import swp490.g7.OnlineLearningSystem.entities.study_result.domain.StudyResult;
//import swp490.g7.OnlineLearningSystem.entities.study_result.repository.StudyResultRepository;
//import swp490.g7.OnlineLearningSystem.entities.subject.domain.Subject;
//import swp490.g7.OnlineLearningSystem.entities.test.domain.Test;
//import swp490.g7.OnlineLearningSystem.entities.test.repository.TestRepository;
//import swp490.g7.OnlineLearningSystem.entities.user.domain.User;
//import swp490.g7.OnlineLearningSystem.entities.user.repository.UserRepository;
//import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@ExtendWith(MockitoExtension.class)
//public class PackageServiceTest {
//
//    @Mock
//    private LessonRepository lessonRepository;
//
//    @Mock
//    private StudyResultRepository studyResultRepository;
//
//    @Mock
//    private TestRepository testRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private PackServiceImpl packService;
//
//    private static List<User> users;
//    private static List<Test> tests;
//    private static List<Subject> subjects;
//    private static List<Lesson> lessons;
//    private List<StudyResult> studyResults;
//
//    @BeforeEach
//    private void setup() {
//        subjects = buildSubjects();
//        tests = buildTests();
//        users = buildUser();
//        lessons = buildLesson();
//        studyResults = buildStudyResult();
//    }
//
//    private static List<StudyResult> buildStudyResult() {
//        StudyResult studyResult = new StudyResult();
//        studyResult.setStudyResultId(1L);
//        studyResult.setUserId(users.get(0).getUserId());
//        studyResult.setIsCompleted(Boolean.TRUE);
//        studyResult.setIsPassed(Boolean.TRUE);
//        studyResult.setLessonId(lessons.get(0).getLessonId());
//        studyResult.setSubjectId(subjects.get(0).getSubjectId());
//        studyResult.setLessonResult(80.0);
//
//        StudyResult studyResult1 = new StudyResult();
//        studyResult1.setStudyResultId(2L);
//        studyResult1.setUserId(users.get(1).getUserId());
//        studyResult1.setIsCompleted(Boolean.FALSE);
//        studyResult1.setIsPassed(Boolean.FALSE);
//        studyResult1.setLessonId(lessons.get(0).getLessonId());
//        studyResult1.setSubjectId(subjects.get(0).getSubjectId());
//        studyResult1.setLessonResult(30.0);
//
//        List<StudyResult> studyResults = new ArrayList<>();
//        studyResults.add(studyResult);
//        studyResults.add(studyResult1);
//        return studyResults;
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
//        test.setTestType(4);
//        test.setSubject(buildSubjects().get(0));
//        test.setPassRate(80);
//        test.setDuration(50);
//        test.setDescription("This test for demo with subject Java core");
//        Test test1 = new Test();
//        test1.setTestId(2L);
//        test1.setName("This test for full test");
//        test1.setStatus(Boolean.TRUE);
//        test1.setTestType(4);
//        test1.setSubject(buildSubjects().get(0));
//        test1.setPassRate(80);
//        test1.setDuration(50);
//        test1.setDescription("This full test with subject Java core");
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
//    @org.junit.jupiter.api.Test
//    void getGradeLessons_success() {
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
//        Mockito.lenient().when(lessonRepository.getLessonQuizBySubjectId(1L)).thenReturn(lessons);
//        List<Long> testIds = lessons.stream()
//                .map(Lesson::getTestId)
//                .collect(Collectors.toList());
//        Mockito.lenient().when(testRepository.findByTestIdIn(testIds)).thenReturn(tests);
//        Mockito.lenient().when(studyResultRepository.getQuizStudyResultByUserIdAndSubjectId(1L, 1L))
//                .thenReturn(studyResults);
//
//        List<GradesDto> grades = packService.getGradeLessons(1L);
//        Assertions.assertTrue(CollectionUtils.isNotEmpty(grades));
//    }
//
//    @org.junit.jupiter.api.Test
//    void getGradeLessons_throwUserNotFound() {
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
//        Mockito.lenient().when(lessonRepository.getLessonQuizBySubjectId(1L)).thenReturn(lessons);
//        List<Long> testIds = lessons.stream()
//                .map(Lesson::getTestId)
//                .collect(Collectors.toList());
//        Mockito.lenient().when(testRepository.findByTestIdIn(testIds)).thenReturn(tests);
//        Mockito.lenient().when(studyResultRepository.getQuizStudyResultByUserIdAndSubjectId(1L, 1L))
//                .thenReturn(studyResults);
//        Assertions.assertThrows(OnlineLearningException.class, () -> {
//            packService.getGradeLessons(1L);
//        });
//    }
//
//    @org.junit.jupiter.api.Test
//    void getGradeLessons_quizLessonsEmpty() {
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
//        Mockito.lenient().when(lessonRepository.getLessonQuizBySubjectId(1L)).thenReturn(null);
//        List<Long> testIds = lessons.stream()
//                .map(Lesson::getTestId)
//                .collect(Collectors.toList());
//        Mockito.lenient().when(testRepository.findByTestIdIn(testIds)).thenReturn(tests);
//        Mockito.lenient().when(studyResultRepository.getQuizStudyResultByUserIdAndSubjectId(1L, 1L))
//                .thenReturn(studyResults);
//
//        List<GradesDto> grades = packService.getGradeLessons(1L);
//        Assertions.assertEquals(grades.size(), 0);
//    }
//
//    @org.junit.jupiter.api.Test
//    void getGradeLessons_throwTestNotFound() {
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
//        Mockito.lenient().when(lessonRepository.getLessonQuizBySubjectId(1L)).thenReturn(lessons);
//        List<Long> testIds = lessons.stream()
//                .map(Lesson::getTestId)
//                .collect(Collectors.toList());
//        Mockito.lenient().when(testRepository.findByTestIdIn(testIds)).thenReturn(null);
//        Mockito.lenient().when(studyResultRepository.getQuizStudyResultByUserIdAndSubjectId(1L, 1L))
//                .thenReturn(studyResults);
//        Assertions.assertThrows(OnlineLearningException.class, () -> {
//            packService.getGradeLessons(1L);
//        });
//    }
//}
