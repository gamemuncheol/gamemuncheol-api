package com.gamemoonchul.application;

import com.gamemoonchul.domain.status.SearchStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SearchServiceTest {
    @Autowired
    SearchService searchService;

    @Test
    @DisplayName("이상한 검색어 넣었을 때 사용자 찾을 수 없음 에러 출력되는지 테스트")
    void search_wrong_user() {
        // given
        String keyword = "아ㅣ롬니ㅏㅇ롬ㄴ아ㅓㅣ롦ㄴ아ㅓ롬ㄴ아ㅓㅣ롬ㄴ아롬너ㅏㄷㄱㅎ저ㅘㄱ호ㅓㅈㅂㅎ#kr1";

        // when
        Exception exception = assertThrows(Exception.class, () -> {
            searchService.search(keyword);
        });

        // then
        assertEquals(SearchStatus.SEARCH_RESULT_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("정상적인 검색어 넣었을 때 에러 없이 검색되는지 테스트")
    void search_no_exception() {
        // given
        String keyword = "rookedsysc#kr1";
        // when
        // then
        assertDoesNotThrow(() -> {
            searchService.search(keyword);
        });
    }
}