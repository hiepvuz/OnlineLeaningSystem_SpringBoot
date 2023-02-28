package swp490.g7.OnlineLearningSystem.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BeanUtility {
    private static final Logger logger = LogManager.getLogger(BeanUtility.class);

    public static <T> T cloneObject(T target) throws IOException {
        ObjectMapper mapper = ObjectMapperUtility.createObjectMapper(true, true);
        return mapper.readValue(
                mapper.writeValueAsString(target),
                mapper.getTypeFactory().constructType(target.getClass())
        );
    }

    public static <T> List<T> cloneObjects(List<?> sources, Class<T> clazz) {
        ObjectMapper objectMapper = ObjectMapperUtility.createObjectMapper(true, true);
        List<T> myObjects = null;
        try {
            myObjects = objectMapper.readValue(
                    objectMapper.writeValueAsString(sources),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, clazz)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (myObjects == null) {
            return new ArrayList<>();
        }
        return myObjects;
    }

    public static <T> T convertValue(Object source, Class<T> targetClass) {
        ObjectMapper objectMapper = ObjectMapperUtility.createObjectMapper(false, true);
        return objectMapper.convertValue(source, targetClass);
    }

    public static <T> T mapObject(InputStream inputStream, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = ObjectMapperUtility.createObjectMapper(false, true);
            return objectMapper.readValue(inputStream, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        ModelMapper modelMapper = new ModelMapper();
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}
