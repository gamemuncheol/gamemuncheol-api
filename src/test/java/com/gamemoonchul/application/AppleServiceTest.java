package com.gamemoonchul.application;

import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.config.apple.AppleIDTokenValidator;
import com.gamemoonchul.config.apple.entities.AppleCredential;
import com.gamemoonchul.config.oauth.user.OAuth2Provider;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import com.gamemoonchul.infrastructure.web.dto.AppleSignUpRequestDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@Transactional
@SpringBootTest
public class AppleServiceTest {

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  AppleService appleService;

  @Autowired
  AppleIDTokenValidator appleIDTokenValidator;

  /**
   * 모의개체 생성
   */
   @Mock
    private AppleIDTokenValidator mockAppleIDTokenValidator;
   @InjectMocks
  private AppleService mockAppleService;


    private AppleCredential mockAppleUserInfo;
    private AppleSignUpRequestDto validSignUpRequest;
    private AppleSignUpRequestDto invalidNameSignUpRequest;

    @BeforeEach
    void setUp() {
        // 테스트 실행 전 필요한 객체를 초기화
        mockAppleUserInfo = new AppleCredential();

        validSignUpRequest = new AppleSignUpRequestDto("validToken", "John Doe"); // 유효한 가입 요청 객체
        invalidNameSignUpRequest = new AppleSignUpRequestDto("validToken", null); // 이름이 유효하지 않은 가입 요청 객체

        // AppleIDTokenValidator의 extractAppleUserinfoFromIDToken 메서드가 "validToken"을 받으면 mockAppleUserInfo를 반환하도록 설정
        when(mockAppleIDTokenValidator.extractAppleUserinfoFromIDToken("validToken")).thenReturn(mockAppleUserInfo);
    }

    @Test
    @DisplayName("유효한 토큰으로 Apple User Info 받아올 수 있는지를 검증한다.")
    void validateRequestWithValidTokenReturnsUserInfo() {
        // 유효한 토큰과 이름으로 validateRequest 메서드를 호출하고 결과를 검증
        AppleCredential result = mockAppleService.validateRequest(validSignUpRequest);

        // 반환된 AppleUserInfo 객체가 기대한 이름과 이메일을 가지고 있는지 확인
        assertEquals("John Doe", result.getName());
    }

    @Test
    @DisplayName("이름이 없는 경우 회원가입 ApiException이 발생하는지 검증한다.")
    void validateRequestWithInvalidNameThrowsApiException() {
        // 이름이 유효하지 않을 때 validateRequest 메서드를 호출하면 ApiException이 발생하는지 확인
        assertThrows(ApiException.class, () -> {
            mockAppleService.validateRequest(invalidNameSignUpRequest);
        });
    }


  @Test
  @DisplayName("동일한 이메일, provider, identifier를 가진 회원이 이미 존재하는 경우 회원이 중복 생성되지 않는지 검증한다.")
  public void signIn() {
    // given
    AppleCredential appleUserInfo = AppleCredential.builder()
        .issuer("yourIssuer")
        .name("yourName")
        .sub("yourUniqueIdentifier")
        .clientId("yourClientId")
        .expiryTime("yourExpiryTime")
        .issuingTime("yourIssuingTime")
        .nonce("yourNonce")
        .email("yourEmail")
        .emailVerified(true)
        .build();

    // when
    appleService.signIn(appleUserInfo);
    appleService.signIn(appleUserInfo);
    List<Member> members = memberRepository.findAllByEmailAndProviderAndIdentifier(appleUserInfo.getEmail(), OAuth2Provider.APPLE, appleUserInfo.getSub());

    // then
    assertEquals(members.size(), 1);
  }
}