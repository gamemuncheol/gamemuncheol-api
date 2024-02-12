package com.gamemoonchul.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.domain.status.SearchStatus;
import com.gamemoonchul.domain.vo.SearchedGame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.ProcessResult;
import org.zeroturnaround.exec.stream.LogOutputStream;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
public class SearchService {
    public List<SearchedGame> search(String keyword) {
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
            return searchedGames;
        } catch (IOException e) {
            throw new ApiException(SearchStatus.FAILED_PARSE_JSON);
        }
    }
}