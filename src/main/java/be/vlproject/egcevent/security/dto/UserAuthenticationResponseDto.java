package be.vlproject.egcevent.security.dto;

public class UserAuthenticationResponseDto {

    private final String username;
    private final String token;

    public UserAuthenticationResponseDto(final String username, final String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}
