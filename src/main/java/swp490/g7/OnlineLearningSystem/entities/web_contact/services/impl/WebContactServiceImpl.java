package swp490.g7.OnlineLearningSystem.entities.web_contact.services.impl;

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
import swp490.g7.OnlineLearningSystem.entities.setting.domain.Setting;
import swp490.g7.OnlineLearningSystem.entities.user.domain.User;
import swp490.g7.OnlineLearningSystem.entities.user.repository.UserRepository;
import swp490.g7.OnlineLearningSystem.entities.web_contact.domain.WebContact;
import swp490.g7.OnlineLearningSystem.entities.web_contact.domain.request.WebContactRequestDto;
import swp490.g7.OnlineLearningSystem.entities.web_contact.domain.respone.WebContactResponseDto;
import swp490.g7.OnlineLearningSystem.entities.web_contact.repository.WebContactRepository;
import swp490.g7.OnlineLearningSystem.entities.web_contact.services.WebContactService;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
import swp490.g7.OnlineLearningSystem.utilities.BeanUtility;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.List;
import java.util.Optional;

@Service
public class WebContactServiceImpl implements WebContactService {

    private static final Logger logger = LogManager.getLogger(WebContactServiceImpl.class);

    @Autowired
    private WebContactRepository webContactRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public PaginationResponse findAll(Pageable pageable) {
        PagedListHolder pagedListHolder =
                new PagedListHolder(webContactRepository.findAll());
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
    public WebContactResponseDto save(WebContactRequestDto request) {
        logger.info("Starting create web-contact!");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request must note be empty");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }
        WebContact webContact = BeanUtility.convertValue(request, WebContact.class);
        WebContact newWebContact = webContactRepository.save(webContact);
        logger.info("web-contact created success!");
        return BeanUtility.convertValue(newWebContact, WebContactResponseDto.class);
    }

    @Override
    public WebContactResponseDto findById(Long id) {
        Optional<WebContact> webContact = webContactRepository.findById(id);
        if (!webContact.isPresent()) {
            logger.error("Can't not found web-contact with id : {}", id);
            throw new OnlineLearningException(ErrorTypes.WEB_CONTACT_NOT_FOUND, id.intValue());
        }
        return BeanUtility.convertValue(webContact.get(), WebContactResponseDto.class);
    }

    @Override
    public WebContactResponseDto update(Long id, WebContactRequestDto request) {
        logger.info("Starting update web-contact");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request must note be empty");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }
        Optional<WebContact> webContactExist = webContactRepository.findById(id);
        if (!webContactExist.isPresent()) {
            logger.error("Can't not found web-contact with id : {}", id);
            throw new OnlineLearningException(ErrorTypes.WEB_CONTACT_NOT_FOUND, id.intValue());
        }
        WebContact webContact = webContactExist.get();
        webContact.setAddress(request.getAddress());
        webContact.setEmail(request.getEmail());
        webContact.setPhoneNumber(request.getPhoneNumber());
        webContact.setFullName(request.getFullName());
        webContact.setMessage(request.getMessage());
        webContact.setResponse(request.getResponse());
        webContact.setUserId(request.getUserId());
        webContact.setSettingId(request.getSettingId());
        webContact.setStatus(request.getStatus());
        webContactRepository.save(webContact);
        logger.info("Update web-contact success");
        WebContactResponseDto webContactResponse = BeanUtility.convertValue(webContact, WebContactResponseDto.class);
        if (ObjectUtils.isNotEmpty(webContactResponse.getUserId())) {
            webContactResponse.setUsername(userRepository.getUsernameByUserId(webContactResponse.getUserId()));
        }
        return webContactResponse;
    }

    @Override
    public void deleteById(Long id) {
        Optional<WebContact> webContact = webContactRepository.findById(id);
        if (!webContact.isPresent()) {
            logger.error("Can't not found web-contact with id : {}", id);
            throw new OnlineLearningException(ErrorTypes.WEB_CONTACT_NOT_FOUND, id.intValue());
        }
        webContactRepository.deleteById(id);
        logger.info("Delete web-contact success");
    }

    @Override
    public PaginationResponse findByCurrentAssign(Pageable pageable) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> existUser = userRepository.findByUsername(userDetails.getUsername());

        if (!existUser.isPresent()) {
            logger.error("User not found with user name: {}", userDetails.getUsername());
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND, userDetails.getUsername());
        }
        String roles = existUser.get().getRoles().toString();
        List<WebContact> webContacts = null;
        if (roles.contains(Setting.USER_ROLE_SUPPORTER)) {
            webContacts = webContactRepository.findByUserIdOrUserIdIsNull(existUser.get().getUserId());
        } else {
            webContacts = webContactRepository.findAll();
        }
        List<WebContactResponseDto> webContactResponses = BeanUtility.mapList(webContacts, WebContactResponseDto.class);
        if (CollectionUtils.isNotEmpty(webContactResponses)) {
            webContactResponses.forEach(w -> {
                if (ObjectUtils.isNotEmpty(w.getUserId())) {
                    w.setUsername(userRepository.getUsernameByUserId(w.getUserId()));
                }
            });
        }
        PagedListHolder pagedListHolder =
                new PagedListHolder(webContactResponses);
        pagedListHolder.setPage(pageable.getPageNumber());
        pagedListHolder.setPageSize(pageable.getPageSize());

        return PaginationResponse.builder()
                .total(pagedListHolder.getSource().size())
                .numberOfPage(pagedListHolder.getPageCount())
                .pageIndex(pageable.getPageNumber())
                .items(pagedListHolder.getPageList())
                .build();
    }
}
