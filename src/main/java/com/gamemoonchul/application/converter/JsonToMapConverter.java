package com.gamemoonchul.application.converter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.Map;

@Converter
public class JsonToMapConverter<K, V> implements AttributeConverter<Map<K, V>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final TypeReference<Map<K, V>> typeRef;

    public JsonToMapConverter(TypeReference<Map<K, V>> typeRef) {
        this.typeRef = typeRef;
    }

    @Override
    public String convertToDatabaseColumn(Map<K, V> attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting Map to JSON", e);
        }
    }

    @Override
    public Map<K, V> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, typeRef);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to Map", e);
        }
    }
}