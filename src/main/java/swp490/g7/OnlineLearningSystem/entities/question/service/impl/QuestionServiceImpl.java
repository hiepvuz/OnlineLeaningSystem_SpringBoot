package swp490.g7.OnlineLearningSystem.entities.question.service.impl;

import com.poiji.bind.Poiji;
import net.lingala.zip4j.ZipFile;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import swp490.g7.OnlineLearningSystem.amazon.service.AmazonClient;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.ContentGroup;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto.ContentGroupDto;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto.ContentGroupExportDto;
import swp490.g7.OnlineLearningSystem.entities.content_group.repository.ContentGroupRepository;
import swp490.g7.OnlineLearningSystem.entities.lesson.repository.LessonRepository;
import swp490.g7.OnlineLearningSystem.entities.question.domain.AnswerOption;
import swp490.g7.OnlineLearningSystem.entities.question.domain.Question;
import swp490.g7.OnlineLearningSystem.entities.question.domain.dto.ExcelQuestionDto;
import swp490.g7.OnlineLearningSystem.entities.question.domain.dto.QuestionExportDto;
import swp490.g7.OnlineLearningSystem.entities.question.domain.request.QuestionRequestDto;
import swp490.g7.OnlineLearningSystem.entities.question.domain.response.AnswerOptionResponseDto;
import swp490.g7.OnlineLearningSystem.entities.question.domain.response.QuestionResponseDto;
import swp490.g7.OnlineLearningSystem.entities.question.repository.QuestionRepository;
import swp490.g7.OnlineLearningSystem.entities.question.service.QuestionService;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.Subject;
import swp490.g7.OnlineLearningSystem.entities.subject.repository.SubjectRepository;
import swp490.g7.OnlineLearningSystem.errorhandling.CommonErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
import swp490.g7.OnlineLearningSystem.utilities.BeanUtility;
import swp490.g7.OnlineLearningSystem.utilities.Constants;
import swp490.g7.OnlineLearningSystem.utilities.ExtensionFileUtility;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    private static final Logger logger = LogManager.getLogger(QuestionServiceImpl.class);

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ContentGroupRepository contentGroupRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private AmazonClient amazonClient;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Value("${amazonProperties.region}")
    private String region;

    @Override
    public PaginationResponse filter(Long subjectId, Long testId, Long lessonId, String body, Long groupId, Pageable pageable) {
        List<QuestionResponseDto> questionResponse = questionRepository.filter(subjectId, testId, lessonId, body);
        List<Long> questionIds = questionResponse.stream()
                .map(QuestionResponseDto::getQuestionId)
                .collect(Collectors.toList());
        List<Question> questions = questionRepository.findQuestionByQuestionIdIn(questionIds);
        questionResponse.forEach(qr -> {
            questions.forEach(q -> {
                if (Objects.equals(qr.getQuestionId(), q.getQuestionId())) {
                    qr.setContentGroups(BeanUtility.mapList(Arrays.asList(q.getContentGroups().toArray()), ContentGroupDto.class));
                }
            });
        });
        if (ObjectUtils.isNotEmpty(groupId)) {
            Set<QuestionResponseDto> filterGroupId = new HashSet<>();
            questionResponse.forEach(q -> {
                q.getContentGroups().forEach(c -> {
                    if (c.getGroupId() == groupId) {
                        filterGroupId.add(q);
                    }
                });
            });
            PagedListHolder pagedListHolder = new PagedListHolder(Arrays.asList(filterGroupId.toArray()));
            pagedListHolder.setPage(pageable.getPageNumber());
            pagedListHolder.setPageSize(pageable.getPageSize());

            return PaginationResponse.builder()
                    .total(pagedListHolder.getSource().size())
                    .numberOfPage(pagedListHolder.getPageCount())
                    .pageIndex(pageable.getPageNumber())
                    .items(pagedListHolder.getPageList())
                    .build();
        }
        PagedListHolder pagedListHolder = new PagedListHolder(questionResponse);
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
    public QuestionResponseDto findById(Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if (!question.isPresent()) {
            logger.error("Question not found with id : {}", questionId);
            throw new OnlineLearningException(ErrorTypes.QUESTION_NOT_FOUND, questionId.toString());
        }
        return mapQuestion(question.get());
    }

    @Override
    public QuestionResponseDto create(QuestionRequestDto request) {
        logger.info("Start creating question...");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request must be not null or empty");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }
        if (CollectionUtils.isEmpty(request.getAnswerOptions())) {
            logger.error("Request must be not null or empty");
            throw new OnlineLearningException(ErrorTypes.ANSWER_OPTION_IS_EMPTY);
        }
        Optional<Subject> subject = subjectRepository.findById(request.getSubjectId());
        if (!subject.isPresent()) {
            logger.error("Subject not found with subject id {}", request.getSubjectId());
            throw new OnlineLearningException(ErrorTypes.SUBJECT_WITH_ID_NOT_FOUND, request.getSubjectId().toString());
        }
        if (ObjectUtils.isEmpty(request.getBody())) {
            logger.error("Body question must not be empty or blank");
            throw new OnlineLearningException(ErrorTypes.BODY_QUESTION_MUST_NOT_BE_EMPTY_OR_BLANK);
        }
        Question question = new Question();
        question.setBody(request.getBody());
        question.setSubject(subject.get());
        question.setExplanation(request.getExplanation());
        question.setSource(request.getSource());
        question.setPage(request.getPage());
        question.setLessonId(request.getLessonId());
        if (ObjectUtils.isNotEmpty(request.getTestId())) {
            question.setTestId(request.getTestId());
        }
        List<AnswerOption> answerOption = BeanUtility.mapList(request.getAnswerOptions(), AnswerOption.class);
        answerOption.forEach(a -> {
            if (StringUtils.isNotBlank(a.getAnswerText())) {
                a.setQuestion(question);
            }
        });
        question.setAnswerOptions(answerOption);
        List<ContentGroup> contentGroupIds = contentGroupRepository.findByGroupIdIn(request.getContentGroupIds());
        if (CollectionUtils.isNotEmpty(contentGroupIds)) {
            Set<ContentGroup> contentGroups = new HashSet<>(contentGroupIds);
            question.setContentGroups(contentGroups);
        }
        questionRepository.save(question);
        logger.info("Successfully create question...");
        return mapQuestion(question);
    }

    @Override
    public QuestionResponseDto update(Long questionId, QuestionRequestDto request) {
        logger.info("Start updating question...");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request must be not null or empty");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }
        Optional<Subject> subject = subjectRepository.findById(request.getSubjectId());
        if (!subject.isPresent()) {
            logger.error("Subject not found with subject id {}", request.getSubjectId());
            throw new OnlineLearningException(ErrorTypes.SUBJECT_WITH_ID_NOT_FOUND, request.getSubjectId().toString());
        }
        if (ObjectUtils.isEmpty(request.getBody())) {
            logger.error("Body question must not be empty or blank");
            throw new OnlineLearningException(ErrorTypes.BODY_QUESTION_MUST_NOT_BE_EMPTY_OR_BLANK);
        }
        Optional<Question> question = questionRepository.findById(questionId);
        question.get().setBody(request.getBody());
        List<AnswerOption> answerOption = BeanUtility.mapList(request.getAnswerOptions(), AnswerOption.class);
        answerOption.forEach(a -> {
            if (StringUtils.isNotBlank(a.getAnswerText())) {
                a.setQuestion(question.get());
            }
        });
        question.get().setAnswerOptions(answerOption);
        question.get().setSubject(subject.get());
        question.get().setExplanation(request.getExplanation());
        question.get().setSource(request.getSource());
        question.get().setPage(request.getPage());
        question.get().setLessonId(request.getLessonId());
        if (ObjectUtils.isNotEmpty(request.getTestId())) {
            question.get().setTestId(request.getTestId());
        }
        List<ContentGroup> contentGroupIds = contentGroupRepository.findByGroupIdIn(request.getContentGroupIds());
        if (CollectionUtils.isNotEmpty(contentGroupIds)) {
            Set<ContentGroup> contentGroups = new HashSet<>(contentGroupIds);
            question.get().setContentGroups(contentGroups);
        }
        questionRepository.save(question.get());
        logger.info("Successfully update question...");
        return mapQuestion(question.get());
    }

    @Override
    public QuestionResponseDto getQuestionById(Long questionId) {
        boolean existQuestion = questionRepository.existsById(questionId);
        if (!existQuestion) {
            logger.error("Question not found with question id: {}", questionId);
            throw new OnlineLearningException(ErrorTypes.QUESTION_NOT_FOUND, questionId.toString());
        }
        return questionRepository.getQuestionById(questionId);
    }

    @Override
    public void delete(Long questionId) {
        logger.info("Start deleting question...");
        Optional<Question> existQuestion = questionRepository.findById(questionId);
        if (!existQuestion.isPresent()) {
            logger.error("Question not found with question id: {}", questionId);
            throw new OnlineLearningException(ErrorTypes.QUESTION_NOT_FOUND, questionId.toString());
        }
        if (!StringUtils.isEmpty(existQuestion.get().getImageUrl())
                || !StringUtils.isBlank(existQuestion.get().getImageUrl())) {
            String[] fileName = existQuestion.get().getImageUrl().split("/");
            amazonClient.deleteFile(fileName[fileName.length - 1]);
        }
        questionRepository.delete(existQuestion.get());
        logger.info("Successfully delete question...");
    }

    @Override
    public String imageUpload(Long questionId, MultipartFile file) {
        logger.info("Upload image with question id: {}", questionId);
        Optional<Question> existQuestion = questionRepository.findById(questionId);
        if (!existQuestion.isPresent()) {
            logger.error("Question not found with question id: {}", questionId);
            throw new OnlineLearningException(ErrorTypes.QUESTION_NOT_FOUND, questionId.toString());
        }
        String fileName = amazonClient.uploadFile(file);
        if (StringUtils.isEmpty(fileName) || StringUtils.isBlank(fileName)) {
            logger.error("Image file upload failed, Question create canceled with question id {}!", questionId);
            questionRepository.delete(existQuestion.get());
        }
        existQuestion.get().setImageUrl(fileName);
        questionRepository.save(existQuestion.get());
        logger.info("Upload image successfully with question id: {}", questionId);
        return fileName;
    }

    @Override
    public void importQuestion(Long subjectId, Long lessonId, Long classLessonId, Long testId, MultipartFile zip) throws IOException {
        if (ObjectUtils.isEmpty(lessonId) && ObjectUtils.isEmpty(testId)) {
            logger.info("Test id or lesson i must not be empty");
            throw new OnlineLearningException(CommonErrorTypes.INVALID_INPUT);
        }
        File tempFile = null;
        String filePath = null;
        File unzipFile = null;
        try {
            tempFile = File.createTempFile(UUID.randomUUID().toString(), Constants.ZIP_EXTENSION);
            FileUtils.copyInputStreamToFile(zip.getInputStream(), tempFile);
            filePath = tempFile.getPath();
            ZipFile zipFile = new ZipFile(filePath);
            zipFile.extractAll(filePath.replace(Constants.ZIP_EXTENSION, Constants.BLANK_STRING));
            unzipFile = new File(String.format("%s", filePath.replace(Constants.ZIP_EXTENSION, Constants.BLANK_STRING)));
            if (ArrayUtils.isEmpty(unzipFile.listFiles())) {
                logger.error("Folder is empty!");
                throw new OnlineLearningException(ErrorTypes.EMPTY_FOLDER);
            }
            List<String> fileNames = Arrays.stream(Objects.requireNonNull(unzipFile.listFiles()))
                    .map(File::getName)
                    .collect(Collectors.toList());
            unzipFile = new File(String.format("%s/%s", filePath.replace(Constants.ZIP_EXTENSION, Constants.BLANK_STRING),
                    fileNames.stream().findFirst().get()));
            List<String> subFileNames = Arrays.stream(Objects.requireNonNull(unzipFile.listFiles()))
                    .map(File::getPath)
                    .collect(Collectors.toList());
            List<File> imageList = new ArrayList<>();
            List<File> csvFile = new ArrayList<>();
            subFileNames.forEach(f -> {
                if (Constants.CSV_FORMAT.equalsIgnoreCase(ExtensionFileUtility.getExtensionByGuava(f)) ||
                        Constants.XLSX_FORMAT.equalsIgnoreCase(ExtensionFileUtility.getExtensionByGuava(f))) {
                    csvFile.add(new File(f));
                }
                if (Constants.PNG_FORMAT.equalsIgnoreCase(ExtensionFileUtility.getExtensionByGuava(f)) ||
                        Constants.JPG_FORMAT.equalsIgnoreCase(ExtensionFileUtility.getExtensionByGuava(f))) {
                    imageList.add(new File(f));
                }
            });
            List<String> imageUrlAfterPut = new ArrayList<>();
            imageList.forEach(i -> {
                imageUrlAfterPut.add(ExtensionFileUtility.generateImageUrl(bucketName, region,
                        amazonClient.generateFileName(i)));
            });
            if (csvFile.size() != 1) {
                logger.error("Just import only excel file");
                throw new OnlineLearningException(ErrorTypes.FILE_UPLOAD_FAILED);
            }
            convertDataQuestionFromExcel(subjectId, testId, lessonId, classLessonId, csvFile.get(0), imageUrlAfterPut);

            imageList.forEach(f -> amazonClient.uploadFile(f));
        } catch (Exception e) {
            logger.error("Import Question failed!");
            throw new OnlineLearningException(ErrorTypes.FILE_UPLOAD_FAILED);
        } finally {
            FileUtils.deleteQuietly(tempFile);
            FileUtils.deleteQuietly(unzipFile);
            assert filePath != null;
            FileUtils.deleteDirectory(new File(String.format("%s", filePath.replace(Constants.ZIP_EXTENSION,
                    Constants.BLANK_STRING))));
        }
    }

    @Override
    public List<QuestionExportDto> exportQuestion(Long subjectId, Long testId, Long lessonId, String body, Long groupId) {
        List<QuestionResponseDto> questionFilter = questionRepository.filter(subjectId, testId, lessonId, body);
        List<Question> questions = questionRepository.findByQuestionIdIn(
                questionFilter.stream()
                        .map(QuestionResponseDto::getQuestionId)
                        .collect(Collectors.toList()));

        List<QuestionExportDto> questionResponses = new ArrayList<>();
        questions.forEach(q -> {
            QuestionExportDto questionExportDto = new QuestionExportDto();
            questionExportDto.setQuestionId(q.getQuestionId());
            questionExportDto.setBody(q.getBody());
            questionExportDto.setSubjectId(q.getSubject().getSubjectId());
            questionExportDto.setSubjectName(q.getSubject().getSubjectName());
            questionExportDto.setTestId(q.getTestId());
            questionExportDto.setLessonId(q.getLessonId());
            questionExportDto.setExplanation(q.getExplanation());
            questionExportDto.setSource(q.getSource());
            questionExportDto.setPage(q.getPage());
            questionExportDto.setAnswerOptions(BeanUtility.mapList(q.getAnswerOptions(), AnswerOptionResponseDto.class));
            questionExportDto.setContentGroups(BeanUtility.mapList(new ArrayList<>(q.getContentGroups()),
                    ContentGroupExportDto.class));
            questionResponses.add(questionExportDto);
        });
        return questionResponses;
    }

    private QuestionResponseDto mapQuestion(Question question) {
        QuestionResponseDto questionResponseDto = new QuestionResponseDto();
        questionResponseDto.setQuestionId(question.getQuestionId());
        questionResponseDto.setBody(question.getBody());
        questionResponseDto.setSubjectId(question.getSubject().getSubjectId());
        questionResponseDto.setSubjectName(question.getSubject().getSubjectName());
        questionResponseDto.setTestId(question.getTestId());
        questionResponseDto.setImageUrl(question.getImageUrl());
        questionResponseDto.setExplanation(question.getExplanation());
        questionResponseDto.setSource(question.getSource());
        questionResponseDto.setPage(question.getPage());
        questionResponseDto.setLessonId(question.getLessonId());
        questionResponseDto.setLessonName(lessonRepository.getNameLessonById(question.getLessonId()));
        questionResponseDto.setContentGroups(BeanUtility.mapList(Arrays.asList(question.getContentGroups().toArray()), ContentGroupDto.class));
        questionResponseDto.setAnswerOptions(BeanUtility.mapList(question.getAnswerOptions(), AnswerOptionResponseDto.class));
        return questionResponseDto;
    }

    private String generateFileName(File file) {
        return new Date().getTime() + "-" + file.getName().replace(" ", "_");
    }

    @Transactional(rollbackFor = {Exception.class})
    public void convertDataQuestionFromExcel(Long subjectId, Long testId, Long lessonId, Long classLessonId,
                                             File file, List<String> imagePath) {
        List<ExcelQuestionDto> questionConverted = Poiji.fromExcel(file, ExcelQuestionDto.class);
        questionConverted.forEach(q -> {
            if (ObjectUtils.isEmpty(q.getBody())) {
                logger.error("Body question must not be empty or blank");
                throw new OnlineLearningException(ErrorTypes.BODY_QUESTION_MUST_NOT_BE_EMPTY_OR_BLANK);
            }
        });
        Optional<Subject> existSubject = subjectRepository.findById(subjectId);
        if (!existSubject.isPresent()) {
            logger.error("Subject not found with subject id: {}", subjectId);
            throw new OnlineLearningException(ErrorTypes.SUBJECT_WITH_ID_NOT_FOUND, subjectId.toString());
        }
        if (CollectionUtils.isNotEmpty(imagePath)) {
            imagePath.forEach(i -> {
                questionConverted.forEach(q -> {
                    if (ObjectUtils.isNotEmpty(q.getImageUrl())) {
                        if (i.contains(q.getImageUrl())) {
                            q.setImageUrl(i);
                        }
                    }
                });
            });
        }
        questionConverted.forEach(q -> {
            if (ObjectUtils.isNotEmpty(q.getImageUrl()) && !q.getImageUrl().contains(bucketName)) {
                logger.error("Image name: {} not match with any image import", q.getImageUrl());
                throw new OnlineLearningException(ErrorTypes.IMAGE_NAME_NOT_MATCH_WITH_ANY_IMAGE, q.getImageUrl());
            }
        });
        List<Question> questions = new ArrayList<>();
        questionConverted.forEach(q -> {
            Question question = new Question();
            if (ObjectUtils.isNotEmpty(q.getContentGroupIds())) {
                List<Long> groupIds = Arrays.asList(q.getContentGroupIds().split(",")).stream()
                        .map(s -> Long.parseLong(s.trim()))
                        .collect(Collectors.toList());

                List<ContentGroup> contentGroups = contentGroupRepository.findByGroupIdIn(groupIds);
                if (CollectionUtils.isEmpty(contentGroups)) {
                    logger.error("Content group not found with id: {}", q.getContentGroupIds());
                    throw new OnlineLearningException(ErrorTypes.CONTENT_GROUP_NOT_FOUND, q.getContentGroupIds());
                }
                question.setContentGroups(new HashSet<>(contentGroups));
            }
            if (ObjectUtils.isNotEmpty(question.getQuestionId())) {
                question.setQuestionId(q.getQuestionId());
            }
            question.setSubject(existSubject.get());
            question.setBody(q.getBody());
            question.setImageUrl(q.getImageUrl());
            question.setPage(q.getPage());
            question.setExplanation(q.getExplanation());
            question.setSource(q.getSource());
            question.setTestId(testId);
            question.setLessonId(lessonId);
            question.setClassLessonId(classLessonId);
            List<AnswerOption> answerOptions = mappingAnswerOption(q, question);
            question.setAnswerOptions(answerOptions);
            questions.add(question);
        });
        questionRepository.saveAll(questions);
    }

    private List<AnswerOption> mappingAnswerOption(ExcelQuestionDto excelQuestion, Question question) {
        String[] isKey = excelQuestion.getIsKey().split(",");
        List<AnswerOption> answerOptions = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(excelQuestion.getAnswerOption1())) {
            answerOptions.add(createAnswerOption(question, excelQuestion.getAnswerOption1(), isKeyCheck(isKey, "1")));
        }
        if (ObjectUtils.isNotEmpty(excelQuestion.getAnswerOption2())) {
            answerOptions.add(createAnswerOption(question, excelQuestion.getAnswerOption2(), isKeyCheck(isKey, "2")));
        }
        if (ObjectUtils.isNotEmpty(excelQuestion.getAnswerOption3())) {
            answerOptions.add(createAnswerOption(question, excelQuestion.getAnswerOption3(), isKeyCheck(isKey, "3")));
        }
        if (ObjectUtils.isNotEmpty(excelQuestion.getAnswerOption4())) {
            answerOptions.add(createAnswerOption(question, excelQuestion.getAnswerOption4(), isKeyCheck(isKey, "4")));
        }
        if (ObjectUtils.isNotEmpty(excelQuestion.getAnswerOption5())) {
            answerOptions.add(createAnswerOption(question, excelQuestion.getAnswerOption5(), isKeyCheck(isKey, "5")));
        }
        if (ObjectUtils.isNotEmpty(excelQuestion.getAnswerOption6())) {
            answerOptions.add(createAnswerOption(question, excelQuestion.getAnswerOption6(), isKeyCheck(isKey, "6")));
        }
        return answerOptions;
    }

    private AnswerOption createAnswerOption(Question question, String answerText, Boolean isKey) {
        AnswerOption answerOption = new AnswerOption();
        answerOption.setQuestion(question);
        answerOption.setAnswerText(answerText);
        answerOption.setIsKey(isKey);
        return answerOption;
    }

    private Boolean isKeyCheck(String[] isKey, String noOption) {
        return Arrays.stream(isKey).anyMatch(noOption::equalsIgnoreCase);
    }
}
