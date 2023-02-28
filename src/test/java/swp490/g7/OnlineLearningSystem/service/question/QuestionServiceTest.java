//package swp490.g7.OnlineLearningSystem.service.question;
//
//import org.apache.commons.io.IOUtils;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.web.multipart.MultipartFile;
//import swp490.g7.OnlineLearningSystem.amazon.service.AmazonClient;
//import swp490.g7.OnlineLearningSystem.entities.content_group.repository.ContentGroupRepository;
//import swp490.g7.OnlineLearningSystem.entities.lesson.domain.Lesson;
//import swp490.g7.OnlineLearningSystem.entities.lesson.repository.LessonRepository;
//import swp490.g7.OnlineLearningSystem.entities.question.domain.AnswerOption;
//import swp490.g7.OnlineLearningSystem.entities.question.domain.Question;
//import swp490.g7.OnlineLearningSystem.entities.question.repository.QuestionRepository;
//import swp490.g7.OnlineLearningSystem.entities.question.service.impl.QuestionServiceImpl;
//import swp490.g7.OnlineLearningSystem.entities.subject.domain.Subject;
//import swp490.g7.OnlineLearningSystem.entities.subject.repository.SubjectRepository;
//import swp490.g7.OnlineLearningSystem.entities.test.domain.Test;
//import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
//import swp490.g7.OnlineLearningSystem.utilities.MockMultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@ExtendWith(MockitoExtension.class)
//public class QuestionServiceTest {
//    @Mock
//    private QuestionRepository questionRepository;
//
//    @Mock
//    private SubjectRepository subjectRepository;
//
//    @Mock
//    private ContentGroupRepository contentGroupRepository;
//
//    @Mock
//    private LessonRepository lessonRepository;
//
//    @Mock
//    private AmazonClient amazonClient;
//
//    @InjectMocks
//    private QuestionServiceImpl questionService;
//
//    private static List<Subject> subjects;
//    private static List<Question> questions;
//    private static List<Lesson> lessons;
//    private static List<Test> tests;
//
//    @BeforeEach
//    private void setup() {
//        subjects = buildSubjects();
//        tests = buildTests();
//        lessons = buildLesson();
//        tests = buildTests();
//        questions = buildQuestion();
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
//    @org.junit.jupiter.api.Test
//    void questionImportForLesson_success() throws IOException {
//        File questionImportFile = new File("src/main/resources/question_import.zip");
//        FileInputStream input = new FileInputStream(questionImportFile);
//        MultipartFile questionImportMultipartFile = new MockMultipartFile("file",
//                questionImportFile.getName(), "text/plain", IOUtils.toByteArray(input));
//
//        Mockito.lenient().when(subjectRepository.findById(1L)).thenReturn(Optional.of(subjects.get(0)));
//        Mockito.lenient().when(questionRepository.saveAll(questions)).thenReturn(questions);
//        questionService.importQuestion(1L, 1L, null, null, questionImportMultipartFile);
//        Assertions.assertTrue(Boolean.TRUE);
//    }
//
//    @org.junit.jupiter.api.Test
//    void questionImportForTest_success() throws IOException {
//        File questionImportFile = new File("src/main/resources/question_import.zip");
//        FileInputStream input = new FileInputStream(questionImportFile);
//        MultipartFile questionImportMultipartFile = new MockMultipartFile("file",
//                questionImportFile.getName(), "text/plain", IOUtils.toByteArray(input));
//
//        Mockito.lenient().when(subjectRepository.findById(1L)).thenReturn(Optional.of(subjects.get(0)));
//        Mockito.lenient().when(questionRepository.saveAll(questions)).thenReturn(questions);
//        questionService.importQuestion(1L, null, null, 1L, questionImportMultipartFile);
//        Assertions.assertTrue(Boolean.TRUE);
//    }
//
//    @org.junit.jupiter.api.Test
//    void questionImportForTest_throwFileUploadFailed() throws IOException {
//        Mockito.lenient().when(subjectRepository.findById(1L)).thenReturn(Optional.of(subjects.get(0)));
//        Mockito.lenient().when(questionRepository.saveAll(questions)).thenReturn(questions);
//        Assertions.assertThrows(AssertionError.class, () -> {
//            questionService.importQuestion(1L, null, null, 1L, null);
//        });
//    }
//
//    @org.junit.jupiter.api.Test
//    void questionImportForLesson_throwFileUploadFailed() throws IOException {
//        Mockito.lenient().when(subjectRepository.findById(1L)).thenReturn(Optional.of(subjects.get(0)));
//        Mockito.lenient().when(questionRepository.saveAll(questions)).thenReturn(questions);
//        Assertions.assertThrows(AssertionError.class, () -> {
//            questionService.importQuestion(1L, null, null, 1L, null);
//        });
//    }
//
//    @org.junit.jupiter.api.Test
//    void questionImportForTest_throwSubjectNotFound() throws IOException {
//        File questionImportFile = new File("src/main/resources/question_import.zip");
//        FileInputStream input = new FileInputStream(questionImportFile);
//        MultipartFile questionImportMultipartFile = new MockMultipartFile("file",
//                questionImportFile.getName(), "text/plain", IOUtils.toByteArray(input));
//
//        Mockito.lenient().when(subjectRepository.findById(1L)).thenReturn(Optional.empty());
//        Mockito.lenient().when(questionRepository.saveAll(questions)).thenReturn(questions);
//        Assertions.assertThrows(OnlineLearningException.class, () -> {
//            questionService.importQuestion(1L, null, null, 1L, questionImportMultipartFile);
//        });
//    }
//
//    @org.junit.jupiter.api.Test
//    void questionImportForLesson_throwSubjectNotFound() throws IOException {
//        File questionImportFile = new File("src/main/resources/question_import.zip");
//        FileInputStream input = new FileInputStream(questionImportFile);
//        MultipartFile questionImportMultipartFile = new MockMultipartFile("file",
//                questionImportFile.getName(), "text/plain", IOUtils.toByteArray(input));
//
//        Mockito.lenient().when(subjectRepository.findById(1L)).thenReturn(Optional.empty());
//        Mockito.lenient().when(questionRepository.saveAll(questions)).thenReturn(questions);
//        Assertions.assertThrows(OnlineLearningException.class, () -> {
//            questionService.importQuestion(1L, 1L, null, null, questionImportMultipartFile);
//        });
//    }
//}
