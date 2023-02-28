package swp490.g7.OnlineLearningSystem.entities.web_contact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swp490.g7.OnlineLearningSystem.entities.web_contact.domain.request.WebContactRequestDto;
import swp490.g7.OnlineLearningSystem.entities.web_contact.domain.respone.WebContactResponseDto;
import swp490.g7.OnlineLearningSystem.entities.web_contact.services.WebContactService;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import javax.validation.Valid;

@RestController
@RequestMapping("/web-contact")
public class WebContactController {

    @Autowired
    private WebContactService webContactService;

    @GetMapping("/all")
    public PaginationResponse findAll(Pageable pageable) {
        return webContactService.findAll(pageable);
    }

    @PostMapping("")
    public WebContactResponseDto save(@Valid @RequestBody WebContactRequestDto request) {
        return webContactService.save(request);
    }

    @GetMapping("{id}")
    public WebContactResponseDto findById(@PathVariable Long id) {
        return webContactService.findById(id);
    }

    @PutMapping("/{id}")
    public WebContactResponseDto update(@PathVariable Long id, @Valid @RequestBody WebContactRequestDto request) {
        return webContactService.update(id, request);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        webContactService.deleteById(id);
    }

    @GetMapping("/filter")
    public PaginationResponse findByCurrentAssign(Pageable pageable) {
        return webContactService.findByCurrentAssign(pageable);
    }

}
