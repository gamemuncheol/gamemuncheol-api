package com.gamemoonchul.application.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Converter
@Component
@RequiredArgsConstructor
public class JsonStringListConverter implements AttributeConverter<List<String>, String> {
    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new RuntimeException("JSON writing error", e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return List.of();
        }
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<String>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("JSON reading error", e);
        }
    }
}
