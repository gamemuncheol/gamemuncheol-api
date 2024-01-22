package com.gamemoonchul.web;

import com.gamemoonchul.infrastructure.web.LectureCommandController;
import com.gamemoonchul.application.LectureQueryService;
import com.gamemoonchul.application.command.LectureCommandHandler;
import com.gamemoonchul.application.command.LectureRegistrationCommand;
import com.gamemoonchul.application.command.TicketCommandHandler;
import com.gamemoonchul.domain.Lecture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("강연 커맨드 API 테스트")
@WebMvcTest(LectureCommandController.class)
class LectureCommandControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LectureCommandHandler lectureCommandHandler;

    @MockBean
    private LectureQueryService lectureQueryService;

    @MockBean
    private TicketCommandHandler ticketCommandHandler;

    @DisplayName("강연 등록 요청 테스트")
    @Test
    void registrationSuccess() throws Exception {
        // Given
        var requestJson = String.format("""
                {
                    "speaker": "kim",
                    "venue" : "우리집",
                    "content": "내용없음333",
                    "capacityOfAttendee": 2,
                    "startAt": "2024-01-04T05:03:42"
                }
                """);
        var mockLecture = Lecture.builder()
                .speaker("kim")
                .venue("우리집")
                .content("내용없음")
                .startAt(LocalDateTime.now())
                .capacityOfAttendee(10)
                .build();

        Mockito.when(lectureCommandHandler.handler(any(LectureRegistrationCommand.class)))
                .thenReturn(mockLecture);

        // When && Then
        mockMvc.perform(
                        post("/api/v1/lectures")
                                .content(requestJson)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.code").value("0000"))
                .andExpect(jsonPath("$.message").value("정상"));
    }
}