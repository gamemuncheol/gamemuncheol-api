package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.ports.output.RiotApiPort;
import com.gamemoonchul.domain.model.vo.riot.MatchRecord;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Deprecated
@RequiredArgsConstructor
@RestControllerWithEnvelopPattern
@RequestMapping("/open-api/riot")
public class RiotApiTestController {
    private final RiotApiPort riotApiPort;

    @GetMapping("/search-matches/2m-200requests/{gameId}")
    public MatchRecord searchMatch2MinutesPer100Request(@PathVariable(name = "gameId") String gameId) {
        MatchRecord finalResult = null;
        int totalRequests = 199;  // 총 200번 호출
        int maxRequestsPerSecond = 9;  // 1초당 최대 10번 호출
        int intervalBetweenBatches = 1000 / maxRequestsPerSecond;  // 각 요청 간격 (ms)

        try {
            for (int i = 0; i < totalRequests; i++) {
                finalResult = riotApiPort.searchMatch("KR_" + gameId);

                // 1초에 최대 10번 호출을 넘지 않도록 슬립 처리
                if ((i + 1) % maxRequestsPerSecond == 0) {
                    Thread.sleep(1000);  // 1초 대기
                } else {
                    Thread.sleep(intervalBetweenBatches);  // 요청 간격만큼 대기
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("API 요청이 중단되었습니다.", e);
        }

        return finalResult;
    }

    @GetMapping("/search-matches/1s-10requests/{gameId}")
    public MatchRecord searchMatch1SecondPer10Request(@PathVariable(name = "gameId") String gameId) throws Exception {
        int requestCnt = 20;
        ExecutorService executor = Executors.newFixedThreadPool(requestCnt); // 스레드 풀 생성
        List<Future<MatchRecord>> futures = new ArrayList<>();

        // 10개의 비동기 요청을 생성
        for (int i = 0; i < requestCnt; i++) {
            futures.add(executor.submit(() -> riotApiPort.searchMatch("KR_" + gameId)));
        }

        MatchRecord finalResult = null;

        // 각 Future의 결과를 가져옴
        for (Future<MatchRecord> future : futures) {
            try {
                MatchRecord result = future.get(1, TimeUnit.SECONDS); // 1초 안에 응답 대기
                finalResult = result; // 마지막 응답을 저장
            } catch (TimeoutException e) {
                System.out.println("요청 시간 초과: " + e.getMessage());
            }
        }

        return finalResult;
    }


    @GetMapping("/search-matches/{gameId}")
    public MatchRecord searchMatch(@PathVariable(name = "gameId") String gameId) {
        return riotApiPort.searchMatch("KR_" + gameId);
    }

}
