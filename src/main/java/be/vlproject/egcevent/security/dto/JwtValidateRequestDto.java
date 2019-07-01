package be.vlproject.egcevent.security.dto;

public class JwtValidateRequestDto {

    private final String bearer;

    public JwtValidateRequestDto(final String bearer) {
        this.bearer = bearer;
    }

    public String getBearer() {
        return bearer;
    }
}
