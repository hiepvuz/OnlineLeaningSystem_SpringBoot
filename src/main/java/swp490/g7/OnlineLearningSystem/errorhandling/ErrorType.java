package swp490.g7.OnlineLearningSystem.errorhandling;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ErrorType {
    String getErrorCode();

    String getErrorDetail();

    int getStatusCode();

    class ErrorTypeFactory {
        private static Map<String, ErrorType> map = new HashMap<>();

        public static synchronized void addTypes(ErrorType... errorTypes) {
            addTypes(Arrays.asList(errorTypes));
        }

        public static synchronized void addTypes(List<ErrorType> errorTypes) {
            errorTypes.forEach(type -> {
                map.put(type.getErrorCode(), type);
            });
        }

        public static ErrorType getErrorType(String errorCode) {
            return map.get(errorCode);
        }
    }
}
