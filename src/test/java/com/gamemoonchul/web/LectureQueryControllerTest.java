package com.gamemoonchul.web;

import com.gamemoonchul.domain.Lecture;
import com.gamemoonchul.infrastructure.persistence.LectureJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("강연 쿼리 API 테스트")
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(LectureCommandController.class)
class LectureQueryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LectureJpaRepository lectureJpaRepository;

    @DisplayName("강연 목록 조회 테스트, 강연시작 7일전 노출 and 1일 후 미노출")
    @Test
    void registrationSuccess() throws Exception {
        // Given
        var currentTime = LocalDateTime.now();

        var mockLecture10Later = Lecture.builder()
                .speaker("mockLecture10Later")
                .venue("우리집")
                .content("내용없음")
                .startAt(currentTime.plusDays(10)) // 10일 이후 강의시작: 미노출
                .capacityOfAttendee(10)
                .build();
        lectureJpaRepository.saveAndFlush(mockLecture10Later);

        var mockLecture5Later = Lecture.builder()
                .speaker("mockLecture5Later")
                .venue("우리집")
                .content("내용없음")
                .startAt(currentTime.plusDays(5)) // 5일 이후 강의시작 : 노출
                .capacityOfAttendee(10)
                .build();
        lectureJpaRepository.saveAndFlush(mockLecture5Later);

        var mockLecture2Pass = Lecture.builder()
                .speaker("mockLecture2Pass")
                .venue("우리집")
                .content("내용없음")
                .startAt(currentTime.minusDays(2)) // 이미 2일전에 강의 시작 : 미노출
                .capacityOfAttendee(10)
                .build();
        lectureJpaRepository.saveAndFlush(mockLecture2Pass);

        var mockLecture1Pass = Lecture.builder()
                .speaker("mockLecture1Pass")
                .venue("우리집")
                .content("내용없음")
                .startAt(currentTime.minusHours(23)) // 이미 23시간전에 강의 시작 : 노출
                .capacityOfAttendee(10)
                .build();
        lectureJpaRepository.saveAndFlush(mockLecture1Pass);


        // When && Then
        mockMvc.perform(
                        get("/api/v1/lectures")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.code").value("0000"))
                .andExpect(jsonPath("$.message").value("정상"))
                .andExpect(jsonPath("$.data.content.length()").value(2))
                .andExpect(jsonPath("$.data.content[0].speaker").value("mockLecture5Later"))
                .andExpect(jsonPath("$.data.content[1].speaker").value("mockLecture1Pass"));
    }


}