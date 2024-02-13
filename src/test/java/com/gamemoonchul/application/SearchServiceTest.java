package com.gamemoonchul.application;

import com.gamemoonchul.domain.redisEntity.RedisCachedGame;
import com.gamemoonchul.domain.status.SearchStatus;
import com.gamemoonchul.infrastructure.repository.redis.RedisCachedGameRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SearchServiceTest {
    @Autowired
    SearchService searchService;
    @Autowired
    RedisCachedGameRepository redisSearchedGameRepository;

    @AfterEach
    void tearDown() {
        redisSearchedGameRepository.deleteAll();
    }

    @Test
    @DisplayName("이상한 검색어 넣었을 때 사용자 찾을 수 없음 에러 출력되는지 테스트")
    void searchWrongUser() {
        // given
        String keyword = "아ㅣ롬니ㅏㅇ롬ㄴ아ㅓㅣ롦ㄴ아ㅓ롬ㄴ아ㅓㅣ롬ㄴ아롬너ㅏㄷㄱㅎ저ㅘㄱ호ㅓㅈㅂㅎ#kr1";

        // when
        Exception exception = assertThrows(Exception.class, () -> {
            searchService.cachedSearch(keyword);
        });

        // then
        assertEquals(SearchStatus.SEARCH_RESULT_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("정상적인 검색어 넣었을 때 에러 없이 검색되는지 테스트")
    void searchNoException() {
        // given
        String keyword = "rookedsysc#kr1";
        // when
        // then
        assertDoesNotThrow(() -> {
            searchService.cachedSearch(keyword);
        });
    }

    @Test
    @DisplayName("동일한 검색을 두 번해도 Redis Cache에 저장된 결과는 1개인지 테스트")
    void verifyDuplicateSearches() {
        // given
        String keyword = "rookedsysc#kr1";
        String searchedKeyword = "";
        // when

        List<RedisCachedGame> result1 = searchService.cachedSearch(keyword);
        if (!result1.isEmpty()) {
            RedisCachedGame firstGame = result1.get(0);
            searchedKeyword = firstGame.getSearchKeyword();
        }

        List<RedisCachedGame> redisCache1 = redisSearchedGameRepository.findAllBySearchKeyword(searchedKeyword);

        List<RedisCachedGame> result2 = searchService.cachedSearch(keyword);
        if (!result2.isEmpty()) {
            RedisCachedGame firstGame = result2.get(0);
            searchedKeyword = firstGame.getSearchKeyword();
        }

        List<RedisCachedGame> redisCache2 = redisSearchedGameRepository.findAllBySearchKeyword(searchedKeyword);

        // then
        assertEquals(redisCache1.size(), redisCache2.size());
    }

}