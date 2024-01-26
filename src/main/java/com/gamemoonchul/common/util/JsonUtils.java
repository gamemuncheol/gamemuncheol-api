package com.gamemoonchul.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class JsonUtils {
    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();

        var javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        javaTimeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        javaTimeModule.addSerializer(ZonedDateTime.class,
                new ZonedDateTimeSerializer(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        javaTimeModule.addDeserializer(ZonedDateTime.class, InstantDeserializer.ZONED_DATE_TIME);

        MAPPER.registerModules(
                javaTimeModule
        );

        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    private JsonUtils() {
        throw new UnsupportedOperationException("Util class.");
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }

    public static String toJson(final Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (IOException e) {
            throw new JsonEncodeException(e);
        }
    }

    public static class JsonEncodeException extends RuntimeException {

        private static final long serialVersionUID = 4975703115049362769L;

        public JsonEncodeException(Throwable cause) {
            super(cause);
        }
    }

    public static class JsonDecodeException extends RuntimeException {

        private static final long serialVersionUID = -2651564042039413190L;

        public JsonDecodeException(Throwable cause) {
            super(cause);
        }
    }

    public static <T> T fromJsonFile(String resourceClasspath, Class<T> clazz) {
        if (resourceClasspath == null) {
            return null;
        }

        ClassPathResource classpathResource;
        classpathResource = new ClassPathResource(resourceClasspath);
        try {
            return MAPPER.readValue(classpathResource.getInputStream(), clazz);
        } catch (IOException e) {
            throw new JsonDecodeException(e);
        }
    }

    public static <T> T fromJsonFile(String resourceClasspath, TypeReference<T> typeReference) {
        if (resourceClasspath == null) {
            return null;
        }

        var classpathResource = new ClassPathResource(resourceClasspath);
        try {
            return MAPPER.readValue(classpathResource.getInputStream(), typeReference);
        } catch (IOException e) {
            throw new JsonDecodeException(e);
        }
    }

}
