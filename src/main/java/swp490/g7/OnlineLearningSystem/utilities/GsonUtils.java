package swp490.g7.OnlineLearningSystem.utilities;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class GsonUtils {
    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr);
    }

    public static <T> T stringToObject(String s, Class<T> clazz) {
        return new Gson().fromJson(s, clazz);
    }
}
