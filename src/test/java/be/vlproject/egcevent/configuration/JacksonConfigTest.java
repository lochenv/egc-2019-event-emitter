package be.vlproject.egcevent.configuration;

import be.vlproject.egcevent.rest.domain.ErrorResponse;
import be.vlproject.egcevent.rest.domain.ErrorResponseMixin;
import be.vlproject.egcevent.rest.domain.GoPlayerMixin;
import be.vlproject.egcevent.rest.domain.SendPairingReportMixin;
import be.vlproject.egcevent.security.dto.*;
import be.vlproject.egcevent.tournament.domain.GoPlayer;
import be.vlproject.egcevent.tournament.domain.SendPairingReport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.jackson2.CoreJackson2Module;

import static org.junit.Assert.*;

public class JacksonConfigTest {

    public ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new CoreJackson2Module());

        objectMapper.addMixIn(UserAuthenticationRequestDto.class, UserAuthenticationRequestDtoMixin.class);
        objectMapper.addMixIn(UserAuthenticationResponseDto.class, UserAuthenticationResponseDtoMixin.class);
        objectMapper.addMixIn(JwtValidateRequestDto.class, JwtValidateRequestDtoMixin.class);
        objectMapper.addMixIn(JwtValidateResponseDto.class, JwtValidateResponseDtoMixin.class);
        objectMapper.addMixIn(ErrorResponse.class, ErrorResponseMixin.class);
        objectMapper.addMixIn(GoPlayer.class, GoPlayerMixin.class);
        objectMapper.addMixIn(SendPairingReport.class, SendPairingReportMixin.class);
    }

    @Test
    public void testSerializeSendPairingReport() throws JsonProcessingException {
        SendPairingReport pairingReport = new SendPairingReport();
        pairingReport.addSucceeded(new GoPlayer("John", "Doe", "him@srv.com", "25k"));
        pairingReport.addInError(new GoPlayer("Jane", "Doe", "her@srv.com", "9p"));
        System.out.println(objectMapper.writeValueAsString(pairingReport));
    }
}
