package com.gamemoonchul.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.domain.converter.RedisCachedGameConverter;
import com.gamemoonchul.domain.dto.SearchedGame;
import com.gamemoonchul.domain.redisEntity.RedisCachedGame;
import com.gamemoonchul.domain.status.SearchStatus;
import com.gamemoonchul.infrastructure.repository.redis.RedisCachedGameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.ProcessResult;
import org.zeroturnaround.exec.stream.LogOutputStream;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {

    private final RedisCachedGameConverter redisSearchedGameConverter;
    private final RedisCachedGameRepository redisSearchedGameRepository;

    public List<RedisCachedGame> cachedSearch(String keyword) {
        List<RedisCachedGame> redisSearchedGames = redisSearchedGameRepository.findAllBySearchKeyword(keyword);
        /*
        이전 검색결과가 Optional이거나 비어있으면 검색을 수행
         */
        if (redisSearchedGames.isEmpty()) {
            return search(keyword);
        } else {
            return redisSearchedGames;
        }
    }

    private List<RedisCachedGame> search(String keyword) {
        String pythonScriptPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "lol_scrapers.py").toString();
        StringBuffer buffer = new StringBuffer();
        try {
            ProcessResult processExecutor = new ProcessExecutor().command("python3", pythonScriptPath, keyword)
                    .redirectOutput(
                            new LogOutputStream() {
                                @Override
                                protected void processLine(String s) {
                                    buffer.append(s);
                                }
                            }
                    ).execute();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(SearchStatus.SEARCH_FAILED);
        }

        String jsonResult = buffer.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<SearchedGame> searchedGames = objectMapper.readValue(jsonResult, new TypeReference<List<SearchedGame>>() {
            });
            if (searchedGames.isEmpty()) {
                throw new ApiException(SearchStatus.SEARCH_RESULT_NOT_FOUND);
            }

            /**
             * 검색 결과를 Redis에 저장
             */
            List<RedisCachedGame> saveResult = saveToRedis(keyword, searchedGames);

            return saveResult;
        } catch (IOException e) {
            throw new ApiException(SearchStatus.FAILED_PARSE_JSON);
        }
    }

    private List<RedisCachedGame> saveToRedis(String keyword, List<SearchedGame> searchedGames) {
        List<RedisCachedGame> redisSearchedGames = searchedGames.stream()
                .map(searchedGameVO -> redisSearchedGameConverter.toEntity(keyword, searchedGameVO))
                .collect(Collectors.toList());
        redisSearchedGameRepository.saveAll(redisSearchedGames);
        return redisSearchedGames;
    }
}