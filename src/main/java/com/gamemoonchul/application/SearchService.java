package com.gamemoonchul.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.domain.converter.RedisCachedGameConverter;
import com.gamemoonchul.domain.model.dto.SearchedGame;
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

}