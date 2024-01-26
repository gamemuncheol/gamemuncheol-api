package com.gamemoonchul.web;

import com.gamemoonchul.application.LectureQueryService;
import com.gamemoonchul.application.command.LectureCommandHandler;
import com.gamemoonchul.application.command.TicketCommandHandler;
import com.gamemoonchul.application.command.TicketRegistrationCommand;
import com.gamemoonchul.domain.Lecture;
import com.gamemoonchul.domain.Ticket;
import com.gamemoonchul.domain.dto.LectureDto;
import com.gamemoonchul.infrastructure.web.TicketCommandController;
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

@DisplayName("티켓 커맨드 API 테스트")
@WebMvcTest(TicketCommandController.class)
class TicketCommandControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LectureCommandHandler lectureCommandHandler;

    @MockBean
    private LectureQueryService lectureQueryService;

    @MockBean
    private TicketCommandHandler ticketCommandHandler;

    @DisplayName("티켓 등록 요청 테스트")
    @Test
    void registrationSuccess() throws Exception {
        // Given
        var requestJson = String.format("""
                {
                    "employeeId": "T1234"
                }
                """);
        var mockLecture = Lecture.builder()
                .speaker("kim")
                .venue("우리집")
                .content("내용없음")
                .startAt(LocalDateTime.now())
                .capacityOfAttendee(10)
                .build();
        var mockTicket = Ticket.builder()
                .lecture(mockLecture)
                .employeeId("T1234")
                .build();

        Mockito.when(ticketCommandHandler.handler(any(TicketRegistrationCommand.class)))
                .thenReturn(mockTicket);

        Mockito.when(lectureQueryService.getById(any(Long.class)))
                .thenReturn(LectureDto.from(mockLecture));

        // When && Then
        mockMvc.perform(
                        post("/api/v1/lectures/1/tickets")
                                .content(requestJson)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.code").value("0000"))
                .andExpect(jsonPath("$.message").value("정상"));
    }

    @DisplayName("티켓 등록 벨리데이션 실패(사번 5글자) 테스트")
    @Test
    void validationFail() throws Exception {
        // Given
        var requestJson = String.format("""
                {
                    "employeeId": "T12341111111"
                }
                """);
        var mockLecture = Lecture.builder()
                .speaker("kim")
                .venue("우리집")
                .content("내용없음")
                .startAt(LocalDateTime.now())
                .capacityOfAttendee(10)
                .build();
        var mockTicket = Ticket.builder()
                .lecture(mockLecture)
                .employeeId("T1234")
                .build();

        Mockito.when(ticketCommandHandler.handler(any(TicketRegistrationCommand.class)))
                .thenReturn(mockTicket);

        Mockito.when(lectureQueryService.getById(any(Long.class)))
                .thenReturn(LectureDto.from(mockLecture));

        // When && Then
        mockMvc.perform(
                        post("/api/v1/lectures/1/tickets")
                                .content(requestJson)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().is4xxClientError())
                .andDo(print())
                .andExpect(jsonPath("$.code").value("C400"))
                .andExpect(jsonPath("$.message").value("size must be between 5 and 5"));
    }
}