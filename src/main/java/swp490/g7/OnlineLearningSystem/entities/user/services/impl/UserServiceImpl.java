package swp490.g7.OnlineLearningSystem.entities.user.services.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import swp490.g7.OnlineLearningSystem.amazon.service.AmazonClient;
import swp490.g7.OnlineLearningSystem.auth.domain.payload.request.ChangePasswordRequest;
import swp490.g7.OnlineLearningSystem.entities.permission.domain.response.PermissionResponseDto;
import swp490.g7.OnlineLearningSystem.entities.permission.service.PermissionService;
import swp490.g7.OnlineLearningSystem.entities.setting.domain.Setting;
import swp490.g7.OnlineLearningSystem.entities.setting.repository.SettingRepository;
import swp490.g7.OnlineLearningSystem.entities.user.domain.User;
import swp490.g7.OnlineLearningSystem.entities.user.domain.request.UserRequestDto;
import swp490.g7.OnlineLearningSystem.entities.user.domain.response.UserPermissionResponseDto;
import swp490.g7.OnlineLearningSystem.entities.user.domain.response.UserResponseDto;
import swp490.g7.OnlineLearningSystem.entities.user.repository.UserRepository;
import swp490.g7.OnlineLearningSystem.entities.user.services.UserService;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
import swp490.g7.OnlineLearningSystem.utilities.BeanUtility;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private AmazonClient amazonClient;

    @Override
    public UserResponseDto findUserByUsername(String userName) {
        Optional<User> user = userRepository.findByUsername(userName);
        if (!user.isPresent()) {
            logger.error("Can not found user with user name: {}", userName);
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND, userName);
        }
        return BeanUtility.convertValue(user.get(), UserResponseDto.class);
    }

    @Override
    public UserResponseDto findById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            logger.error("Can not found user with user name: {}", userId);
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND, userId.intValue());
        }
        UserResponseDto userResponseDto = BeanUtility.convertValue(user.get(), UserResponseDto.class);
        userResponseDto.setRoles(userRepository.getRole(userId));
        return userResponseDto;
    }

    //    @Caching(evict = {
//            @CacheEvict(value = "userByName", allEntries = true)
//    })
    @Override
    public UserResponseDto create(UserRequestDto request) {
        logger.info("Starting create user!");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request must not be empty!");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);

        }
        Optional<User> userDuplicate = userRepository.findByUsername(request.getUsername());
        if (userDuplicate.isPresent()) {
            logger.error("user with user name: {} existing!", request.getUsername());
            throw new OnlineLearningException(ErrorTypes.USER_WITH_NAME_ALREADY_EXISTS, request.getUsername());
        }
        User user = BeanUtility.convertValue(request, User.class);
        user.setCreatedDate(new Date());
        User newUser = userRepository.save(user);
        logger.info("user created success!");
        return BeanUtility.convertValue(newUser, UserResponseDto.class);
    }

//    @Caching(evict = {
//            @CacheEvict(value = "userByName", allEntries = true)
//    })
    @Override
    public UserResponseDto update(Long userId, UserRequestDto request) {
        logger.info("Starting update user!");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request is empty with user userId {}", userId);
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }
        Optional<User> existingUser = userRepository.findById(userId);
        if (!existingUser.isPresent()) {
            logger.error("Can not found user with userId {}", userId);
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND);
        }
        UserDetails userDetails = getUserDetails();
        if (ObjectUtils.isEmpty(userDetails)) {
            logger.error("Failed to get current user");
            throw new OnlineLearningException(ErrorTypes.CURRENT_USER_NOT_FOUND);
        }
        Optional<User> currentUser = userRepository.findByUsername(userDetails.getUsername());
        Long currentUserId = currentUser.get().getUserId();
        Set<Object> roles = userRepository.getRole(currentUserId);
        boolean isAdmin = false;
        for (Object o : roles
        ) {
            if ("Admin".equals(String.valueOf(o))) {
                isAdmin = true;
            }
        }
        User user = existingUser.get();
        if (!user.getUsername().equalsIgnoreCase(request.getUsername())) {
            boolean existUser = userRepository.existsByUsername(request.getUsername());
            if (existUser) {
                logger.error("User with user name existed : {}", request.getUsername());
                throw new OnlineLearningException(ErrorTypes.USER_WITH_NAME_ALREADY_EXISTS, user.getUsername());
            }
        }
        user.setUsername(request.getUsername());
        user.setFullName(request.getFullName());
        user.setAddress(request.getAddress());
        user.setGender(request.getGender());
        user.setAvatarUrl(request.getAvatarUrl());
        user.setPhoneNumber(request.getPhoneNumber());
        if (isAdmin) {
            user.setDisabled(request.isDisable());
            Set<String> strRoles = request.getRoles();
            Set<Setting> roleUpdate = new HashSet<>();
            if (strRoles != null) {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "Admin":
                            Setting adminRole = settingRepository.findBySettingTitle(Setting.USER_ROLE_ADMIN);
                            roleUpdate.add(adminRole);
                            break;
                        case "Manager":
                            Setting managerRole = settingRepository.findBySettingTitle(Setting.USER_ROLE_MANAGER);
                            roleUpdate.add(managerRole);
                            break;
                        case "Expert":
                            Setting expertRole = settingRepository.findBySettingTitle(Setting.USER_ROLE_EXPERT);
                            roleUpdate.add(expertRole);
                            break;
                        case "Trainer":
                            Setting trainerRole = settingRepository.findBySettingTitle(Setting.USER_ROLE_TRAINER);
                            roleUpdate.add(trainerRole);
                            break;
                        case "Supporter":
                            Setting supporterRole = settingRepository.findBySettingTitle(Setting.USER_ROLE_SUPPORTER);
                            roleUpdate.add(supporterRole);
                            break;
                        default:
                            Setting traineeRole = settingRepository.findBySettingTitle(Setting.USER_ROLE_TRAINEE);
                            roleUpdate.add(traineeRole);
                    }
                });
                user.setRoles(roleUpdate);
            }
        }
        userRepository.save(user);
        logger.info("Update user successfully!");
        return BeanUtility.convertValue(user, UserResponseDto.class);
    }

    //    @Caching(evict = {
//            @CacheEvict(value = "userByName", allEntries = true)
//    })
    @Override
    public void deleteById(Long userId) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (!existingUser.isPresent()) {
            logger.error("Can not found user with userId {}", userId);
            throw new OnlineLearningException(ErrorTypes.ROLE_NOT_FOUND, userId.toString());
        }
        //Delete user role in database
        logger.info("Delete user roles");
        existingUser.get().setRoles(null);
        userRepository.save(existingUser.get());

        userRepository.deleteById(userId);
        logger.info("Delete user successfully!");
    }

    @Override
    public void disable(Long userId) {
        logger.info("Starting disable user with id {}", userId);
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            logger.error("Can not found user with id {}", userId);
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND);
        }
        user.get().setDisabled(true);
        userRepository.save(user.get());
        logger.info("Successfully disable user with id {}", userId);
    }

    @Override
    public void changePassword(Long userId, ChangePasswordRequest request) {
        logger.info("Starting change password with user userId {}", userId);
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request is empty with user userId {}", userId);
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }

        Optional<User> existingUser = userRepository.findById(userId);
        if (!existingUser.isPresent()) {
            logger.error("Can not found with user userId {}", userId);
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND);
        }

        if (!encoder.matches(request.getOldPassword(), existingUser.get().getPassword())) {
            logger.error("Password is incorrect with user userId {}", userId);
            throw new OnlineLearningException(ErrorTypes.PASSWORD_IS_INCORRECT);
        }
        existingUser.get().setPassword(encoder.encode(request.getNewPassword()));
        existingUser.get().setUpdatedDate(new Date());
        userRepository.save(existingUser.get());
        logger.info("Successfully change password with user userId {}", userId);
    }

    @Override
    public UserResponseDto getCurrentUser() {
        UserDetails userDetails = getUserDetails();
        if (ObjectUtils.isEmpty(userDetails)) {
            logger.error("Failed to get current user");
            throw new OnlineLearningException(ErrorTypes.CURRENT_USER_NOT_FOUND);
        }
        Optional<User> currentUser = userRepository.findByUsername(userDetails.getUsername());
        Long userId = currentUser.get().getUserId();
        UserResponseDto userResponseDto = BeanUtility.convertValue(currentUser.get(), UserResponseDto.class);
        userResponseDto.setRoles(userRepository.getRole(userId));
        return userResponseDto;
    }

    @Override
    public PaginationResponse findAll(Pageable pageable) {
        PagedListHolder pagedListHolder =
                new PagedListHolder(userRepository.findAll());
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
    public void enable(Long userId) {
        logger.info("Starting enable user with id {}", userId);
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            logger.error("Can not found user with id {}", userId);
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND);
        }
        user.get().setDisabled(false);
        userRepository.save(user.get());
        logger.info("Successfully enable user with id {}", userId);
    }

    @Override
    public void newPassword(String email, String newPassword) {
        logger.info("Start set new password for user with email {}", email);
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            logger.error("Can not found user with id {}", email);
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND);
        }
        user.get().setPassword(encoder.encode(newPassword));
        userRepository.save(user.get());
        logger.info("Successfully set new password for user with email {}", email);
    }

    @Override
    public UserPermissionResponseDto getCurrentUserPermission() {
        UserDetails userDetails = getUserDetails();
        if (ObjectUtils.isEmpty(userDetails)) {
            logger.error("Failed to get current user");
            throw new OnlineLearningException(ErrorTypes.CURRENT_USER_NOT_FOUND);
        }
        Optional<User> currentUser = findByUsername(userDetails.getUsername());
        if (ObjectUtils.isEmpty(currentUser.get())) {
            logger.error("Failed to get current user");
            throw new OnlineLearningException(ErrorTypes.CURRENT_USER_NOT_FOUND);
        }
        Optional<Setting> setting = currentUser.get().getRoles().stream().findFirst();
        List<PermissionResponseDto> permissions = permissionService.getByRole(setting.get().getSettingId());
        UserPermissionResponseDto userPermission = BeanUtility.convertValue(currentUser.get(), UserPermissionResponseDto.class);
        userPermission.setPermissions(permissions);
        return userPermission;
    }

    //    @Cacheable(value = "userByName", key = "#userName")
    @Override
    public Optional<User> findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public PaginationResponse filter(String role, Boolean disabled, String username, String phone, Pageable
            pageable) {
        List<User> userResponses = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(role)) {
            List<User> users = userRepository.filter(role, disabled, username, phone, pageable);
            List<User> finalUserResponses = userResponses;
            users.forEach(u -> {
                u.getRoles().forEach(r -> {
                    if (role.equalsIgnoreCase(r.getSettingTitle())) {
                        finalUserResponses.add(u);
                    }
                });
            });
            userResponses = finalUserResponses;
        } else {
            userResponses = userRepository.filter(role, disabled, username, phone, pageable);
        }
        PagedListHolder pagedListHolder =
                new PagedListHolder(userResponses);
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
    public List<Object> findListUserByRole(String displayOrder) {
        Long roleId = settingRepository.findByDisplayOrderAndTypeId(displayOrder, 1L).getSettingId();
        if (ObjectUtils.isEmpty(roleId)) {
            throw new OnlineLearningException(ErrorTypes.USER_ROLE_WITH_DISPLAY_ORDER_NOT_FOUND);
        }
        return userRepository.findByRole(roleId);
    }

    @Override
    public List<User> findByEmailIn(List<String> emails) {
        return userRepository.findByEmailIn(emails);
    }

    @Override
    public void imageUpload(Long id, MultipartFile file) {
        logger.info("Start upload image for user id: {}", id);
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            logger.error("User not found with id: {}", id);
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND);
        }
        String fileName = amazonClient.uploadFile(file);
        if (StringUtils.isEmpty(fileName) || StringUtils.isBlank(fileName)) {
            logger.error("File name is empty, upload file fail !");
            throw new OnlineLearningException(ErrorTypes.FILE_UPLOAD_FAILED);
        }
        user.get().setAvatarUrl(fileName);
        userRepository.save(user.get());
        logger.info("Upload image successfully for user with id: {}", id);
    }

    @Override
    public Set<Object> getAllSupporter() {
        return userRepository.getAllSupporter();
    }

    public Set<Object> getAllTrainer() {
        return userRepository.getAllTrainer();
    }

    @Override
    public Set<String> getEmails() {
        return userRepository.getEmails();
    }

    private static UserDetails getUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
