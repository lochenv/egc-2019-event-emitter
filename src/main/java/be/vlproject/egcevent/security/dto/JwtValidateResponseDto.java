package be.vlproject.egcevent.security.dto;

public class JwtValidateResponseDto {

    private final boolean valid;

    public JwtValidateResponseDto(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }
}
