package com.gamemoonchul.common.util;

import com.gamemoonchul.infrastructure.web.dto.response.MatchUserResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Slf4j
public class PropertyUtil {
    public static Properties loadProperties() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = MatchUserResponse.class.getClassLoader()
                .getResourceAsStream("lolchampion.properties");
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            properties = new Properties();
            properties.load(reader);
        } catch (IOException e) {
            log.error("properties 파일을 읽어오는데 실패했습니다.\n" + e.getMessage() + "\n" + e.getStackTrace());
        }
        return properties;
    }

}
