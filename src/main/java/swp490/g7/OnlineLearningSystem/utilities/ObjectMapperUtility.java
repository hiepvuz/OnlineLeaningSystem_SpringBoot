package swp490.g7.OnlineLearningSystem.utilities;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.text.DecimalFormat;

public class ObjectMapperUtility {

    public static ObjectMapper createObjectMapperForRead() {
        return createObjectMapper(false, true);
    }

    public static ObjectMapper createObjectMapperForWrite() {
        return createObjectMapper(true, false);
    }

    public static ObjectMapper createObjectMapper(boolean forWrite, boolean forRead) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (forRead) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        if (forWrite) {
            SimpleModule module = new SimpleModule("DoubleSerialized");
            module.addSerializer(Double.class, new DoubleSerializer());
            module.addSerializer(double.class, new DoubleSerializer());
            objectMapper.registerModule(module);
        }
        return objectMapper;
    }

    public static class DoubleSerializer extends JsonSerializer<Double> {
        @Override
        public void serialize(Double aDouble, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            DecimalFormat df = new DecimalFormat("#.#");
            df.setMaximumFractionDigits(20);
            jsonGenerator.writeNumber(df.format(aDouble));
        }
    }
}
