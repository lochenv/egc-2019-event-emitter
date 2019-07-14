package be.vlproject.egcevent.security;

public class SecurityConstants {

    public static final String AUTHORIZATION_HEADER = "X-Authorization";
    public static final String JWT_BEARER_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "secure-api";
    public static final String TOKEN_AUDIENCE = "secure-app";
    public static final String AUTH_LOGIN_URL = "/api/authenticate";
    public static final String AUTH_VALID_URL = "/api/authenticate/valid";
}
