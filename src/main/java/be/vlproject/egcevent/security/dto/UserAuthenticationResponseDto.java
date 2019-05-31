package be.vlproject.egcevent.security.dto;

public class UserAuthenticationResponseDto {

    private final String bearer;

    public UserAuthenticationResponseDto(String bearer) {
        this.bearer = bearer;
    }

    public String getBearer() {
        return bearer;
    }
}
