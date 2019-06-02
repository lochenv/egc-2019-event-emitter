package be.vlproject.egcevent.security;

import be.vlproject.egcevent.security.dto.UserAuthenticationRequestDto;
import be.vlproject.egcevent.security.dto.UserAuthenticationResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthenticateResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateResource.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @PostMapping(SecurityConstants.AUTH_LOGIN_URL)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserAuthenticationRequestDto loginRequest) {
//$2a$10$PWLdjEdaKoavFfWjBWr2buGNijA9VYO5mpGcdFHb2vuY522qIdYrW

        LOGGER.info("New authentication request");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtHelper.generateToken(authentication);

        LOGGER.info(authentication.getPrincipal().toString());
        return ResponseEntity.ok(new UserAuthenticationResponseDto(
                ((User) authentication.getPrincipal()).getUsername(), jwt));
    }
}
