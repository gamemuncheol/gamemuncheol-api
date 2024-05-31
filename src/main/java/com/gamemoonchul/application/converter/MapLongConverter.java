package com.gamemoonchul.application.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.persistence.Converter;

import java.util.Map;

@Converter(autoApply = true)
public class MapLongConverter extends JsonToMapConverter<Long, Long> {
    public MapLongConverter() {
        super(new TypeReference<Map<Long, Long>>() {
        });
    }
}