package swp490.g7.OnlineLearningSystem.annotations;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swp490.g7.OnlineLearningSystem.entities.permission.domain.response.PermissionResponseDto;
import swp490.g7.OnlineLearningSystem.entities.user.domain.response.UserPermissionResponseDto;
import swp490.g7.OnlineLearningSystem.entities.user.services.UserService;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class UserPermissionAspect {
    private static final Logger logger = LogManager.getLogger(UserPermissionAspect.class);

    private static final String ALL = "ALL";

    private static final String UPDATE = "UPDATE";

    private static final String ADD = "ADD";

    private static final String DELETE = "DELETE";

    @Autowired
    private UserService userService;

    @Around("@annotation(UserPermission)")
    public Object permissionChecking(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        UserPermission userPermission = method.getAnnotation(UserPermission.class);
        String name = userPermission.name();
        String type = userPermission.type();
        UserPermissionResponseDto user = userService.getCurrentUserPermission();
        PermissionResponseDto permission = null;
        for (PermissionResponseDto per : user.getPermissions()) {
            logger.info(per.getScreenName());
            if (name.equalsIgnoreCase(per.getScreenName())) {
                permission = per;
                break;
            }
        }
        if (ObjectUtils.isEmpty(permission)) {
            logger.error("Permission not define with user id {}", user.getUserId());
            throw new OnlineLearningException(ErrorTypes.PERMISSION_NOT_DEFINE, user.getUserId().toString());
        }
        switch (type) {
            case ALL:
                if (!permission.getAllData()) {
                    throw new OnlineLearningException(ErrorTypes.ACCESS_DENIED);
                }
                break;
            case UPDATE:
                if (!permission.getCanEdit()) {
                    throw new OnlineLearningException(ErrorTypes.ACCESS_DENIED);
                }
                break;
            case DELETE:
                if (!permission.getCanDelete()) {
                    throw new OnlineLearningException(ErrorTypes.ACCESS_DENIED);
                }
                break;
            case ADD:
                if (!permission.getCanAdd()) {
                    throw new OnlineLearningException(ErrorTypes.ACCESS_DENIED);
                }
                break;
            default:
                throw new OnlineLearningException(ErrorTypes.PERMISSION_NOT_DEFINE);
        }
        return proceedingJoinPoint.proceed();
    }

    /**
     * Get parameters Map Set
     *
     * @param joinPoint {@link ProceedingJoinPoint}
     * @return Map<String, Object>
     */

    private Map<String, Object> getNameAndValue(ProceedingJoinPoint joinPoint) {
        Map<String, Object> param = new HashMap<>();
        Object[] paramValues = joinPoint.getArgs();
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();

        for (int i = 0; i < paramNames.length; i++) {
            param.put(paramNames[i], paramValues[i]);
        }
        return param;

    }
}

