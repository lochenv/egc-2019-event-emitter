package be.vlproject.egcevent.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Component
public class JwtHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtHelper.class);

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration}")
    private long expirationTime;

    @Autowired
    private ObjectMapper mapper;

    public String generateToken(final Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();
        String json;
        try {
            json = mapper.writeValueAsString(userPrincipal);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            json = userPrincipal.getUsername();
        }


        return Jwts.builder()
                .setSubject(json)
                .setIssuer(SecurityConstants.TOKEN_ISSUER) // Change it by property
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setIssuedAt(Date.from(
                        LocalDateTime.now()
                                 .atZone(
                                         ZoneId.systemDefault())
                                 .toInstant()))
                .setExpiration(Date.from(
                        LocalDateTime.now()
                                 .plus(expirationTime, ChronoUnit.MINUTES)
                                .atZone(
                                        ZoneId.systemDefault())
                                .toInstant()))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean isValid(final String token) {
        boolean result = false;
        try {
            if (token != null) {
                Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
                result = true;
            }
        } catch (Exception e) {
            LOGGER.error("Cannot parse token for reason", e);
        }

        return result;
    }

    public User extractUser(String token) throws IOException {

        return mapper.readValue(Jwts.parser()
            .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject(),
                User.class);

    }
}
