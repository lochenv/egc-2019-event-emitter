package be.vlproject.egcevent.configuration;

import be.vlproject.egcevent.security.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.jackson2.CoreJackson2Module;

@Configuration
public class JacksonConfig {

    @Primary
    @Autowired
    @Bean("jacksonObjectMapper")
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();

        // Some other custom configuration for supporting Java 8 features
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new CoreJackson2Module());

        objectMapper.addMixIn(UserAuthenticationRequestDto.class, UserAuthenticationRequestDtoMixin.class);
        objectMapper.addMixIn(UserAuthenticationResponseDto.class, UserAuthenticationResponseDtoMixin.class);
        objectMapper.addMixIn(JwtValidateRequestDto.class, JwtValidateRequestDtoMixin.class);
        return objectMapper;
    }
}

