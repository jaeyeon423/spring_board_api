package farm.board.service.sign;

import farm.board.handler.JwtHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {
    @InjectMocks TokenService tokenService;
    @Mock
    JwtHandler jwtHandler;

    @BeforeEach
    void beforeEach() {
        ReflectionTestUtils.setField(tokenService, "accessTokenMaxAgeSeconds", 10L);
        ReflectionTestUtils.setField(tokenService, "refreshTokenMaxAgeSeconds", 10L);
        ReflectionTestUtils.setField(tokenService, "accessKey", "accessKey");
        ReflectionTestUtils.setField(tokenService, "refreshKey", "refreshKey");
    }

    @Test
    public void createAccessTokenTest(){
        //given
        given(jwtHandler.generateJwtToken(anyString(), anyString(), anyLong())).willReturn("access");

        //when
        String token = tokenService.createAccessToken("subject");

        //then
        Assertions.assertThat(token).isEqualTo("access");
        verify(jwtHandler).generateJwtToken(anyString(), anyString(), anyLong());
    }
    @Test
    void createRefreshTokenTest() {
        // given
        given(jwtHandler.generateJwtToken(anyString(), anyString(), anyLong())).willReturn("refresh");

        // when
        String token = tokenService.createRefreshToken("subject");

        // then
        assertThat(token).isEqualTo("refresh");
        verify(jwtHandler).generateJwtToken(anyString(), anyString(), anyLong());
    }
}