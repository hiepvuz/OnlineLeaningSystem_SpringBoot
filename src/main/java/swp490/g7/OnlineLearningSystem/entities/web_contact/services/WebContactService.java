package swp490.g7.OnlineLearningSystem.entities.web_contact.services;

import org.springframework.data.domain.Pageable;
import swp490.g7.OnlineLearningSystem.entities.web_contact.domain.request.WebContactRequestDto;
import swp490.g7.OnlineLearningSystem.entities.web_contact.domain.respone.WebContactResponseDto;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

public interface WebContactService {

    PaginationResponse<Object> findAll(Pageable pageable);

    WebContactResponseDto save(WebContactRequestDto request);

    WebContactResponseDto findById(Long id);

    WebContactResponseDto update(Long id, WebContactRequestDto request);

    void deleteById(Long id);

    PaginationResponse findByCurrentAssign(Pageable pageable);
}
