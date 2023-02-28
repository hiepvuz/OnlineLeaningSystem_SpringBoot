package swp490.g7.OnlineLearningSystem.entities.keyword.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import swp490.g7.OnlineLearningSystem.annotations.UserPermission;
import swp490.g7.OnlineLearningSystem.entities.keyword.domain.request.KeywordRequestDto;
import swp490.g7.OnlineLearningSystem.entities.keyword.domain.response.KeywordResponseDto;
import swp490.g7.OnlineLearningSystem.entities.keyword.service.KeywordService;
import swp490.g7.OnlineLearningSystem.utilities.Constants;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import javax.validation.Valid;

@RestController
@RequestMapping("/keyword")
public class KeywordController {

    @Autowired
    private KeywordService keywordService;

    @GetMapping("/content-group")
    public PaginationResponse findByContentGroup(@RequestParam("contentGroupId") Long contentGroupId,
                                                 @RequestParam("subjectId") Long subjectId,
                                                 Pageable pageable) {
        return keywordService.findByGroupId(contentGroupId, subjectId, pageable);
    }

    @GetMapping("/{id}")
    public KeywordResponseDto findById(@PathVariable Long id) {
        return keywordService.findById(id);
    }

    @UserPermission(name = Constants.KEYWORD_DETAILS, type = Constants.ADD)
    @PostMapping("")
    public KeywordResponseDto create(@Valid @RequestBody KeywordRequestDto request) {
        return keywordService.save(request);
    }

    @UserPermission(name = Constants.KEYWORD_DETAILS, type = Constants.UPDATE)
    @PutMapping("/{id}")
    public KeywordResponseDto update(@PathVariable Long id, @Valid @RequestBody KeywordRequestDto request) {
        return keywordService.update(id, request);
    }

    @UserPermission(name = Constants.KEYWORD_LIST, type = Constants.ALL)
    @GetMapping("/filter")
    public PaginationResponse filter(@RequestParam(value = "keyword", required = false) String keyword,
                                     @RequestParam(value = "status", required = false) Boolean status,
                                     @RequestParam(value = "categoryId", required = false) Long categoryId,
                                     @RequestParam(value = "subjectId") Long subjectId,
                                     @RequestParam(value = "groupId", required = false) Long groupId,
                                     Pageable pageable) {
        return keywordService.filter(keyword, status, categoryId, groupId, subjectId, pageable);
    }

    @UserPermission(name = Constants.KEYWORD_DETAILS, type = Constants.UPDATE)
    @PutMapping("{id}/enable")
    public void enable(@PathVariable("id") Long id) {
        keywordService.enable(id);
    }

    @UserPermission(name = Constants.KEYWORD_DETAILS, type = Constants.UPDATE)
    @PutMapping("{id}/disable")
    public void disable(@PathVariable("id") Long id) {
        keywordService.disable(id);
    }

    @PostMapping("/import-keyword")
    public void importKeywordExcel(@RequestParam("categoryId") Long categoryId, @RequestPart("file") MultipartFile file) {
        keywordService.importKeyWord(categoryId, file);
    }
}
